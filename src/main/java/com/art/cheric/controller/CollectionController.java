package com.art.cheric.controller;

import com.art.cheric.dto.collection.request.CollectionCreationRequestDto;
import com.art.cheric.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/collection")
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping
    public ResponseEntity<String> createNewCollection(@RequestParam(name = "userId") Long userId,
                                                      @RequestBody CollectionCreationRequestDto collectionCreationRequestDto) {
        try {
            Long collectionId = collectionService.createNewCollection(collectionCreationRequestDto, userId);

            return ResponseEntity.ok("사용자 ID: " + userId + ", 컬렉션 ID: " + collectionId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("컬렉션 생성 실패 : " + e.getMessage());
        }
    }

    @PostMapping("add")
    public ResponseEntity<String> addNewArt(
            @RequestParam(name = "artId") Long artId,
            @RequestParam(name = "collectionId") Long collectionId
    ) {
        try {
            collectionService.addNewArt(artId, collectionId);

            return ResponseEntity.ok("컬렉션 ID: " + collectionId + ", 작품 ID: " + artId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("작품 추가 실패 : " + e.getMessage());
        }
    }

}
