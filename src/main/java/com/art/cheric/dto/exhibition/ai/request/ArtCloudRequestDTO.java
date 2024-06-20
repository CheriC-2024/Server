package com.art.cheric.dto.exhibition.ai.request;


import com.art.cheric.constant.CloudRequestType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class ArtCloudRequestDTO {
    @NotNull
    @Enumerated(EnumType.STRING)
    private CloudRequestType cloudRequestType;

    @NotEmpty
    private List<Long> artIds;

}
