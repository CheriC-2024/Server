package com.art.cheric.service.ai.musiczen;

import com.art.cheric.dto.exhibition.ai.request.MusicZenRequestDTO;
import com.art.cheric.dto.exhibition.ai.respond.MusicZenAudioResponseDTO;
import com.art.cheric.dto.exhibition.ai.respond.MusicZenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
public class MusicZenService {

    @Value("${music.upload.dir}")
    private String uploadDir;

    @Value("${replicate.api.token}")
    private String apiToken;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final int MAX_RETRY = 5; // 최대 재시도 횟수
    private static final long WAIT_TIME_MS = 20000; // 재시도 간격 (20초)

    public MusicZenResponseDTO generateMusic(MusicZenRequestDTO request) {
        List<MusicZenAudioResponseDTO> result = new ArrayList<>();

        for (String theme : request.getTheme()) {
            List<String> audioPaths = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                String musicUrl = callMusicZenApiWithPolling(theme);
                String filePath = saveMusicToFile(musicUrl, theme, i);
                audioPaths.add(filePath);
            }
            MusicZenAudioResponseDTO themeMusic = new MusicZenAudioResponseDTO();
            themeMusic.create(theme, audioPaths);
            result.add(themeMusic);
        }

        MusicZenResponseDTO response = new MusicZenResponseDTO();
        response.setResult(result);
        return response;
    }

    private String callMusicZenApiWithPolling(String theme) {
        String predictionId = submitMusicZenApi(theme);
        String musicUrl = pollMusicZenApi(predictionId);
        if (musicUrl == null) {
            throw new RuntimeException("Failed to generate music after polling " + MAX_RETRY + " times");
        }
        return musicUrl;
    }

    private String submitMusicZenApi(String theme) {
        String url = "https://api.replicate.com/v1/predictions";
        Map<String, Object> requestBody = Map.of(
                "version", "671ac645ce5e552cc63a54a2bbff63fcf798043055d2dac5fc9e36a837eedcfb",
                "input", Map.of("text", theme + " 전시의 bgm 생성해")
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);
        return (String) response.get("id");
    }

    private String pollMusicZenApi(String predictionId) {
        int retryCount = 0;
        String musicUrl = null;
        while (retryCount < MAX_RETRY) {
            try {
                Thread.sleep(WAIT_TIME_MS); // 일정 시간 간격으로 재시도
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting to poll API", e);
            }

            String apiUrl = "https://api.replicate.com/v1/predictions/" + predictionId;
            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
            Map<String, Object> prediction = (Map<String, Object>) response.get("data");

            if (prediction != null && prediction.get("url") != null) {
                musicUrl = (String) prediction.get("url");
                break;
            } else {
                retryCount++;
                System.out.println("Retrying API call... Attempt " + retryCount);
            }
        }

        return musicUrl;
    }

    private String saveMusicToFile(String musicUrl, String theme, int index) {
        try {
            byte[] musicData = restTemplate.getForObject(musicUrl, byte[].class);
            java.nio.file.Path filePath = Paths.get(uploadDir, theme + "_" + index + ".mp3");
            Files.write(filePath, musicData);
            return filePath.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to save music file", e);
        }
    }
}
