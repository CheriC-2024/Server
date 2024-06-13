package com.art.cheric.dto.art.respond;

import com.art.cheric.entity.MyArt;
import lombok.Getter;

import java.util.Date;

@Getter
public class MyArtCreateReseponseDto {
    private ArtCreateResponseDto art;
    private Date gottenDate;
    private String gottenPath;
    private String gottenPrice;

    public MyArtCreateReseponseDto of(MyArt myArt) {
        ArtCreateResponseDto artCreateResponseDto = new ArtCreateResponseDto();
        this.art = artCreateResponseDto.of(myArt.getArt());
        this.gottenDate = myArt.getGottenDate();
        this.gottenPath = myArt.getGottenPath();
        this.gottenPrice = myArt.getGottenPrice();
        return this;
    }
}