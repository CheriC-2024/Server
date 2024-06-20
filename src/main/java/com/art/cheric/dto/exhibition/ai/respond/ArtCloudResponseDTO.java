package com.art.cheric.dto.exhibition.ai.respond;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ArtCloudResponseDTO {
    private Long artId;
    private List<String> properties;

    public ArtCloudResponseDTO () {
    }

    public ArtCloudResponseDTO (Long artId) {
        this.artId = artId;
        this.properties = new ArrayList<>();
    }
}