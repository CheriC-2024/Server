package com.art.cheric.dto;

import com.art.cheric.constant.ArtStatus;
import com.art.cheric.constant.MaterialSort;
import com.art.cheric.entity.Art;
import com.art.cheric.entity.Image;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
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
    private List<ImageCreateRequestDto> imageList;

    // TODO: 추후 modelmapper class 로 변경하기 > builder 로 하니까 mapping이 제대로 되지 않나봐..
    public Art createArt() {
        List<Image> imageList = new ArrayList<>();
        Art art = Art.builder()
                .name(this.name)
                .series(this.series)
                .description(this.description)
                .widthSize(this.widthSize)
                .heightSize(this.heightSize)
                .materialStatus(this.materialStatus)
                .artStatus(this.artStatus)
                .notice(this.notice)
                .cherryNum(this.cherryNum)
                .build();


        if (this.getImageList() != null && !this.getImageList().isEmpty()) {
            imageList = this.getImageList().stream()
                    .map(image -> {
                        ImageCreateRequestDto imageCreateRequestDto = new ImageCreateRequestDto();
                        return imageCreateRequestDto.createImage();
                    }).toList();
            art.addImageList(imageList);
        }

        return art;
    }

    public ArtCreateRequestDto of(Art art) {

        List<ImageCreateRequestDto> imageCreateRequestDtoList = art.getImageList().stream()
                .map(image -> {
                    ImageCreateRequestDto imageCreateRequestDto = new ImageCreateRequestDto();
                    return imageCreateRequestDto.of(image);
                }).toList();

        this.artStatus = art.getArtStatus();
        this.name = art.getName();
        this.series = art.getSeries();
        this.description = art.getDescription();
        this.widthSize = art.getWidthSize();
        this.heightSize = art.getHeightSize();
        this.materialStatus = art.getMaterialStatus();
        this.notice = art.getNotice();
        this.cherryNum = art.getCherryNum();
        this.imageList = imageCreateRequestDtoList;
        return this;
    }
}
