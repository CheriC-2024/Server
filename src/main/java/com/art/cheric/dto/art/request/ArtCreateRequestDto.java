package com.art.cheric.dto.art.request;

import com.art.cheric.constant.MaterialSort;
import com.art.cheric.constant.PartSort;
import com.art.cheric.constant.Role;
import com.art.cheric.entity.Art;
import lombok.Getter;

import java.util.Date;

@Getter
public class ArtCreateRequestDto {
    private Long artId;
    private String name;
    private String series;
    private String description;
    private Integer widthSize;
    private Integer heightSize;
    private MaterialSort materialStatus;
    private String artist;
    private Date madeAt;
    private PartSort partSort;
    private Integer cherryNum;

    // TODO: 추후 modelmapper class 로 변경하기 > builder 로 하니까 mapping이 제대로 되지 않나봐..
    public Art createArt(Role register) {
        return Art.builder()
                .name(this.name)
                .series(this.series)
                .description(this.description)
                .widthSize(this.widthSize)
                .heightSize(this.heightSize)
                .materialStatus(this.materialStatus)
                .madeAt(this.madeAt)
                .artist(this.artist)
                .partSort(this.partSort)
                .register(register)
                .cherryNum(this.cherryNum)
                .build();
    }

    public ArtCreateRequestDto of(Art art) {
        this.artId = art.getId();
        this.madeAt = art.getMadeAt();
        this.artist = art.getArtist();
        this.partSort = art.getPartSort();
        this.name = art.getName();
        this.series = art.getSeries();
        this.description = art.getDescription();
        this.widthSize = art.getWidthSize();
        this.heightSize = art.getHeightSize();
        this.materialStatus = art.getMaterialStatus();
        this.cherryNum = art.getCherryNum();
        return this;
    }
}
