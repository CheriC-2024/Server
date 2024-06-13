package com.art.cheric.service.ai.cloud;

import com.art.cheric.dto.exhibition.ai.request.ArtCloudRequestDTO;
import com.art.cheric.dto.exhibition.ai.respond.ArtCloudResponseDTO;
import com.art.cheric.entity.Art;
import com.art.cheric.repository.ArtRepository;
import com.art.cheric.util.ColorUtils;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional

public class ArtCloudService {
    private final ArtRepository artRepository;

    // 색상 추출
    public List<ArtCloudResponseDTO> extractColors(ArtCloudRequestDTO artCloudRequestDTO) throws Exception {
        // 최종 응답을 담을 객체
        List<ArtCloudResponseDTO> artCloudResponseDTOS = new ArrayList<>();

        // 중복 id 호출 혹은 30개 이상 호출 에러 탐지
        checkDuplicationAndMaxSize(artCloudRequestDTO.getArtIds());

        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            for (Long artId : artCloudRequestDTO.getArtIds()) {

                Art art = artRepository.findById(artId)
                        .orElseThrow(() -> new IllegalArgumentException("작품 ID 없음 : " + artId));

                // vision api 에 요청 보내고 응답 받기
                BatchAnnotateImagesResponse response = vision.batchAnnotateImages(List.of(makeCloudVisionRequest(art)));

                // 응답 객체 리스트 반환
                List<AnnotateImageResponse> colorResponse = response.getResponsesList();

                // 응답 처리
                ArtCloudResponseDTO artCloudResponseDTO = new ArtCloudResponseDTO(artId);

                // 에러 확인 및 에러 날리기
                for (AnnotateImageResponse res : colorResponse) {
                    if (res.hasError()) {
                       // TODO 그냥 error 찍는 게 나을지, 아니면 아예 에러 던지는 게 나을지 고민해보기
                        System.out.printf("Error: %s\n", res.getError().getMessage());
                        continue;
                    }

                    // 색상 정보 score 에 따라 상위 4개 색상 추출해 hex 값 반환
                    ImageProperties imageProperties = res.getImagePropertiesAnnotation();
                    List<String> dominantColorsHex = imageProperties.getDominantColors().getColorsList().stream()
                            .sorted((c1, c2) -> Float.compare(c2.getScore(), c1.getScore()))
                            .limit(4)
                            .map(colorInfo -> ColorUtils.rgbToHex(colorInfo.getColor().getRed(), colorInfo.getColor().getGreen(), colorInfo.getColor().getBlue()))
                            .toList();

                    // 추출한 색상 정보를 객체에 추가
                    artCloudResponseDTO.getProperties().addAll(dominantColorsHex);
                }

                // 완성된 객체를 최종 응답에 추가
                artCloudResponseDTOS.add(artCloudResponseDTO);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        // 최종 응답 반환
        return artCloudResponseDTOS;
    }


    // 클라이언트에게 요청 받은 artIds 검사하는 메서드
    private void checkDuplicationAndMaxSize(List<Long> artIds) throws IllegalArgumentException {
        Set<Long> uniqueArtIds = new HashSet<>(artIds);
        if (uniqueArtIds.size() != artIds.size()) {
            throw new IllegalArgumentException("중복된 작품 ID가 있습니다.");
        }

        if (artIds.size() > 30) {
            throw new IllegalArgumentException("작품 ID는 최대 30개까지 허용됩니다.");
        }
    }


    // vision api 요청 객체 만들기
    private AnnotateImageRequest makeCloudVisionRequest(Art art) throws IOException {
        // 이미지 파일 생성
        String filePath = art.getImageList().get(0).getFilePath();
        ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));
        Image img = Image.newBuilder().setContent(imgBytes).build();

        // 이미지 속성 탐지를 위한 feature 객체 생성
        Feature feat = Feature.newBuilder().setType(Feature.Type.IMAGE_PROPERTIES).build();

        // 이미지와 기능 포함하는 요청 객체 생성
        return AnnotateImageRequest.newBuilder()
                .addFeatures(feat)
                .setImage(img)
                .build();
    }

}
