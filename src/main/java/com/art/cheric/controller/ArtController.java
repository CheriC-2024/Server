package com.art.cheric.controller;

import com.art.cheric.dto.art.request.ArtistArtCreateRequestDto;
import com.art.cheric.dto.art.request.MyArtCreateRequestDto;
import com.art.cheric.dto.art.respond.ArtistArtCreateResponseDto;
import com.art.cheric.dto.art.respond.MyArtCreateReseponseDto;
import com.art.cheric.exception.UnauthorizedAccessException;
import com.art.cheric.service.ArtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ArtController {

    private final ArtService artService;

    // 작가 작품 생성 api
    @PostMapping("/artist_art")
    public ResponseEntity<String> createArtistArt(@RequestPart(name = "artistArt") ArtistArtCreateRequestDto artistArtCreateRequestDto,
                                                  @RequestPart(name = "artImg") MultipartFile artImg,
                                                  @RequestParam(name = "userId") Long userId) {
        try {
            Long artistArtId = artService.saveArtistArt(artistArtCreateRequestDto, artImg, userId);

            return ResponseEntity.status(HttpStatus.CREATED).body(artistArtId + " 작가 작품이 생성되었습니다.");
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 작가 작품 조회 api
    @GetMapping("/artist_art/{artId}")
    public ResponseEntity<ArtistArtCreateResponseDto> getArtistArtById(@PathVariable(name = "artId") Long artId) {
        try {
            ArtistArtCreateResponseDto artCreateResponseDto = artService.getArtistArtById(artId);

            return ResponseEntity.ok().body(artCreateResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 작가 작품 생성 api
    @PostMapping("/my_art")
    public ResponseEntity<String> createMyArt(@RequestPart(name = "myArt") MyArtCreateRequestDto myArtCreateRequestDto,
                                            @RequestPart(name = "artImg") MultipartFile artImg,
                                            @RequestParam(name = "userId") Long userId) {
        try {
            Long artId = artService.saveMyArt(myArtCreateRequestDto, artImg, userId);

            return ResponseEntity.status(HttpStatus.CREATED).body(artId + " 소장 작품이 생성되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 작가 작품 조회 api
    @GetMapping("/my_art/{artId}")
    public ResponseEntity<MyArtCreateReseponseDto> getMyArtById(@PathVariable(name = "artId") Long artId) {
        try {
            MyArtCreateReseponseDto myArtCreateReseponseDto = artService.getMyArtById(artId);

            return ResponseEntity.ok().body(myArtCreateReseponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
