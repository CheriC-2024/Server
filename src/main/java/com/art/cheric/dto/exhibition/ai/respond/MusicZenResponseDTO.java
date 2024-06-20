package com.art.cheric.dto.exhibition.ai.respond;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MusicZenResponseDTO {
    private List<MusicZenAudioResponseDTO> result;
}