package com.art.cheric.dto.art.respond;

import com.art.cheric.constant.MaterialSort;
import com.art.cheric.constant.PartSort;
import com.art.cheric.constant.Role;
import com.art.cheric.entity.Art;
import lombok.Getter;

import java.util.Date;

@Getter
public class ArtCreateResponseDto {
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
    private Role register;
    private Integer cherryNum;
    private String filePath;

    public ArtCreateResponseDto of(Art art) {
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
        this.register = art.getRegister();
        this.filePath = art.getImageList().get(0).getFilePath();
        return this;
    }
}
