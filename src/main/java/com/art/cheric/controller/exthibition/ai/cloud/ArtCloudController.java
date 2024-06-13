package com.art.cheric.controller.exthibition.ai.cloud;

import com.art.cheric.dto.exhibition.ai.request.ArtCloudRequestDTO;
import com.art.cheric.dto.exhibition.ai.respond.ArtCloudResponseDTO;
import com.art.cheric.dto.exhibition.ai.respond.ArtworkResponseDTO;
import com.art.cheric.service.ai.cloud.ArtCloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cloud")
@RequiredArgsConstructor
public class ArtCloudController {

    private final ArtCloudService artCloudService;

    // 색상 추출 api
    @PostMapping("/color")
    public ResponseEntity<ArtworkResponseDTO> extractColors(
            @RequestBody @Validated ArtCloudRequestDTO request) {

        try {
            List<ArtCloudResponseDTO> attributes = artCloudService.extractColors(request);
            ArtworkResponseDTO responseDTO = new ArtworkResponseDTO();
            responseDTO.setCode(HttpStatus.CREATED.value());
            responseDTO.setMessage("속성이 추출되었습니다.");
            responseDTO.setValue(attributes);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }  catch (IllegalArgumentException e) {
            ArtworkResponseDTO responseDTO = new ArtworkResponseDTO();
            responseDTO.setCode(HttpStatus.NOT_FOUND.value());
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        } catch (Exception e) {
            ArtworkResponseDTO responseDTO = new ArtworkResponseDTO();
            responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }
}