package com.art.cheric.dto.exhibition.ai.respond;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArtworkResponseDTO {
    private int code;
    private String message;
    private List<ArtColorResponseDTO> value;
}
// TODO 추후에 BASEDTO 생성해서 응답 규칙 만들기
// TODO SETTER 대신에 활용할 방법 생각해서 변경하기