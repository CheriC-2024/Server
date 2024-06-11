package com.art.cheric.dto.art.request;

import com.art.cheric.entity.Art;
import com.art.cheric.entity.MyArt;
import com.art.cheric.entity.User;
import lombok.Getter;

import java.util.Date;

@Getter
public class MyArtCreateRequestDto {
    private ArtCreateRequestDto art;
    private Date gottenDate;
    private String gottenPath;
    private String gottenPrice;

    // TODO: 추후 modelmapper class 로 변경하기 > builder 로 하니까 mapping이 제대로 되지 않나봐..
    public MyArt createMyArt(User owner) {
        Art newArt = this.art.createArt();

        return MyArt.builder()
                .art(newArt)
                .user(owner)
                .gottenDate(gottenDate)
                .gottenPath(gottenPath)
                .gottenPrice(gottenPrice)
                .build();
    }

    public MyArtCreateRequestDto of(MyArt myArt) {
        ArtCreateRequestDto artCreateRequestDto = new ArtCreateRequestDto();
        this.art = artCreateRequestDto.of(myArt.getArt());
        this.gottenDate = myArt.getGottenDate();
        this.gottenPath = myArt.getGottenPath();
        this.gottenPrice = myArt.getGottenPrice();
        return this;
    }
}