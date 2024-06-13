package com.art.cheric.dto.exhibition.ai.respond;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArtCloudResponseDTO {
    private Long artId;
    private String artImage;
    private List<String> properties;
}
// TODO SETTER 대신에 활용할 방법 생각해서 변경하기