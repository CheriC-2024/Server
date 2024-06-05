package com.art.cheric.controller;

import com.art.cheric.dto.ArtCreateRequestDto;
import com.art.cheric.service.ArtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/art")
public class ArtController {

    private final ArtService artService;

    // 작품 생성 api
    @PostMapping
    public ResponseEntity<Long> createArt(@RequestPart(name = "artCreateRequestDto") ArtCreateRequestDto artCreateRequestDto,
                                           @RequestPart(name = "artImg") MultipartFile artImg) {
        try {
            Long itemId = artService.saveArt(artCreateRequestDto, artImg);

            return ResponseEntity.status(HttpStatus.CREATED).body(itemId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 작품 조회 api
    @GetMapping("/{artId}")
    public ResponseEntity<ArtCreateRequestDto> getArtById(@PathVariable(name = "artId") Long artId) {
        try {
            ArtCreateRequestDto artCreateRequestDto = artService.getArtById(artId);

            return ResponseEntity.ok().body(artCreateRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
