package com.art.cheric.controller.exhibition.ai.musiczen;

import com.art.cheric.dto.exhibition.ai.request.MusicZenRequestDTO;
import com.art.cheric.dto.exhibition.ai.respond.MusicZenResponseDTO;
import com.art.cheric.service.ai.musiczen.MusicZenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/musiczen")
@RequiredArgsConstructor
public class MusicZenController {
    private final MusicZenService musicZenService;

    @PostMapping
    public ResponseEntity<MusicZenResponseDTO> generateMusic(@RequestBody MusicZenRequestDTO request) {
        try {
            MusicZenResponseDTO musicZenResponseDTO = musicZenService.generateMusic(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(musicZenResponseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
