package com.art.cheric.dto.exhibition.ai.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class ArtCloudRequestDTO {
    @NotEmpty
    private List<Long> artIds;

}
