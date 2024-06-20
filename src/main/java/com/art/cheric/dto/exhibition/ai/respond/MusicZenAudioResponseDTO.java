package com.art.cheric.dto.exhibition.ai.respond;

import lombok.Getter;

import java.util.List;

@Getter
public class MusicZenAudioResponseDTO {
    private String theme;
    private List<String> audio;

    public MusicZenAudioResponseDTO create(String theme, List<String> audio) {
        this.audio = audio;
        this.theme = theme;
        return this;
    }
}
