package com.art.cheric.service.ai.cloud;

import com.art.cheric.dto.exhibition.ai.respond.ArtColorResponseDTO;
import com.art.cheric.entity.Art;
import com.art.cheric.repository.ArtRepository;
import com.art.cheric.util.ColorUtils;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional

public class ArtCloudService {
    private final ArtRepository artRepository;

    public List<ArtColorResponseDTO> extractColors(List<Long> artIds) throws Exception {
        List<ArtColorResponseDTO> artworkAttributesList = new ArrayList<>();
        Set<Long> uniqueArtIds = new HashSet<>(artIds);
        if (uniqueArtIds.size() != artIds.size()) {
            throw new IllegalArgumentException("중복된 작품 ID가 있습니다.");
        }

        // 최대 개수 제한 검사
        if (artIds.size() > 30) {
            throw new IllegalArgumentException("작품 ID는 최대 30개까지 허용됩니다.");
        }

        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            for (Long artId : artIds) {
                Art art = artRepository.findById(artId)
                        .orElseThrow(() -> new IllegalArgumentException("작품 ID 없음 : " + artId));

                String filePath = art.getImageList().get(0).getFilePath();
                ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

                Image img = Image.newBuilder().setContent(imgBytes).build();
                Feature feat = Feature.newBuilder().setType(Feature.Type.IMAGE_PROPERTIES).build();

                AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                        .addFeatures(feat)
                        .setImage(img)
                        .build();
                BatchAnnotateImagesResponse response = vision.batchAnnotateImages(List.of(request));
                List<AnnotateImageResponse> responses = response.getResponsesList();

                ArtColorResponseDTO attributesDTO = new ArtColorResponseDTO();
                attributesDTO.setArtId(artId);
                attributesDTO.setArtImage(filePath);
                attributesDTO.setColors(new ArrayList<>());

                for (AnnotateImageResponse res : responses) {
                    if (res.hasError()) {
                        System.out.printf("Error: %s\n", res.getError().getMessage());
                        continue;
                    }

                    ImageProperties imageProperties = res.getImagePropertiesAnnotation();
                    if (imageProperties != null) {
                        List<String> dominantColorsHex = imageProperties.getDominantColors().getColorsList().stream()
                                .sorted((c1, c2) -> Float.compare(c2.getScore(), c1.getScore()))
                                .limit(4)
                                .map(colorInfo -> ColorUtils.rgbToHex(colorInfo.getColor().getRed(), colorInfo.getColor().getGreen(), colorInfo.getColor().getBlue()))
                                .toList();

                        attributesDTO.getColors().addAll(dominantColorsHex);
                    }
                }
                artworkAttributesList.add(attributesDTO);
            }
        }
        return artworkAttributesList;
    }
}
