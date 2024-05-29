package com.art.cheric.controller;

import com.art.cheric.dto.ArtCreateRequestDto;
import com.art.cheric.service.ArtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class ArtController {

    private final ArtService artService;

    @PostMapping("/art")
    public ResponseEntity<Long> createItem(@RequestPart(name = "artCreateRequestDto") ArtCreateRequestDto artCreateRequestDto,
                                           @RequestPart(name = "artImg") MultipartFile artImg) {

        try {
            Long itemId = artService.saveArt(artCreateRequestDto, artImg);

            return ResponseEntity.status(HttpStatus.CREATED).body(itemId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
