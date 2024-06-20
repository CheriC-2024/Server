package com.art.cheric.controller.exhibition.ai.chatgpt;

import com.art.cheric.constant.ChatGptRequestType;
import com.art.cheric.dto.exhibition.ai.respond.ArtCloudResponseDTO;
import com.art.cheric.dto.exhibition.ai.respond.ChatGptResponseDTO;
import com.art.cheric.service.ai.chatgpt.ChatGptService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatgpt")
public class ChatGPTController {

    private final ChatGptService chatGPTService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<String> generateThemes(@RequestBody List<ArtCloudResponseDTO> request,
                                                 @RequestParam(name = "requestType") ChatGptRequestType chatGptRequestType) {
        try {
            // 모든 properties 추출 및 중복 제거
            List<String> properties = request.stream()
                    .flatMap(art -> art.getProperties().stream())
                    .distinct()
                    .collect(Collectors.toList());

            // properties를 쉼표로 구분하여 문자열로 변환
            String propertiesString = String.join(", ", properties);
            System.out.println(propertiesString);

            // 응답
            String prompt;

            // ChatGPT API 호출 및 응답 처리
            if (chatGptRequestType.equals(ChatGptRequestType.THEME)) {
                prompt =
                        "너는 전시 기획자로 전시의 분위기를 설정할거야" +
                                "밑의 단어들의 작품 전시를 기획하려고 해" +
                                propertiesString + "\n" +
                                "미술, 작품, 디자인 같은 단어는 제외하고," +
                                "그 외의 모든 단어의 의미를 담아 형용사로 만들어, 한국어로 5개 추천해줘" +
                                "이유는 200자 정도의 분량으로 작성해" +
                                "응답은 json 형식으로 {result: [, , , , ], reason: [ , , , , ]} 형식으로 반환해" +
                                "reason은 result와 1:1 대응이야";

            } else { // ChatGptRequestType.TITLE
                prompt =
                        "너는 전시 기획자로 전시의 제목을 설정할거야" +
                                "밑의 단어들의 작품 전시를 기획하려고 해" +
                                propertiesString + "\n" +
                                "미술, 작품, 디자인 같은 단어는 제외하고," +
                                "그 외의 모든 단어의 의미를 담아 제목:부제목 형태로 만들어, 5개 한국어로 추천해줘" +
                                "이유는 200자 정도의 분량으로 작성해" +
                                "응답은 json 형식으로 {result: [, , , , ], reason: [ , , , , ]} 형식으로 반환해" +
                                "reason은 result와 1:1 대응이야";

            }

            ChatGptResponseDTO chatGPTResponse = chatGPTService.getChatGPTResponse(prompt);
            String jsonResponse = objectMapper.writeValueAsString(chatGPTResponse);

            return ResponseEntity.ok(jsonResponse);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Invalid request: " + ex.getMessage());
        } catch (HttpClientErrorException ex) {
            return ResponseEntity.status(ex.getRawStatusCode()).body("Downstream service error: " + ex.getMessage());
        } catch (HttpServerErrorException ex) {
            return ResponseEntity.status(ex.getRawStatusCode()).body("Downstream service unavailable: " + ex.getMessage());
        } catch (JsonProcessingException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("JSON processing error: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred: " + ex.getMessage());
        }
    }
}
