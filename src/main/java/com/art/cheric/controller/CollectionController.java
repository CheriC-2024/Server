package com.art.cheric.controller;

import com.art.cheric.dto.collection.request.CollectionCreationRequestDto;
import com.art.cheric.dto.collection.request.CollectionReadRequestDto;
import com.art.cheric.dto.collection.respond.CollectionResponseDto;
import com.art.cheric.dto.collection.respond.CollectionResponseDto2;
import com.art.cheric.exception.DuplicateEntryException;
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

    // 컬렉션 생성 api
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

    // 컬렉션에 작품 추가 api
    @PostMapping("/add")
    public ResponseEntity<String> addNewArt(
            @RequestParam(name = "artId") Long artId,
            @RequestParam(name = "collectionId") Long collectionId
    ) {
        try {
            collectionService.addNewArt(artId, collectionId);

            return ResponseEntity.ok("컬렉션 ID: " + collectionId + ", 작품 ID: " + artId);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 항목 발생: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("작품 추가 실패 : " + e.getMessage());
        }
    }

    // 컬렉션 리스트 > 작품 리스트 읽기 api
    @PostMapping("/all")
    public ResponseEntity<CollectionResponseDto> getCollectionsByIds(@RequestBody CollectionReadRequestDto collectionReadRequestDto) {
        try {
            return ResponseEntity.ok(collectionService.getCollectionsByIds(collectionReadRequestDto.getCollectionIds()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // 컬렉션 리스트 > 사용자 id 따라 작품 리스트 읽기 api
    @GetMapping
    public ResponseEntity<CollectionResponseDto2> getCollectionsByUserId(@RequestParam(name = "userId") Long userId) {
        try {
            return ResponseEntity.ok(collectionService.getCollectionsByUserId(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
