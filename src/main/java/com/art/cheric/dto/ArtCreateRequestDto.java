package com.art.cheric.dto;

import com.art.cheric.constant.ArtStatus;
import com.art.cheric.constant.MaterialSort;
import com.art.cheric.entity.Art;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Getter
@NoArgsConstructor
public class ArtCreateRequestDto {
    private String name;
    private String series;
    private String description;
    private Integer widthSize;
    private Integer heightSize;
    private ArtStatus artStatus;
    private MaterialSort materialStatus;
    private String notice;
    private int cherryNum;

    private static ModelMapper modelMapper = new ModelMapper();

    public Art createArt(){
        return modelMapper.map(this, Art.class);
    }

    public static ArtCreateRequestDto of(Art art){
        return modelMapper.map(art,ArtCreateRequestDto.class);
    }
}
