package com.art.cheric.dto.collection.respond;

import com.art.cheric.constant.Role;
import com.art.cheric.entity.Art;
import lombok.Getter;

@Getter
public class CollectionArtReadDto {
    private Long artId;
    private String name;
    private Role register;
    private Integer cherryNum;
    private String filePath;

    public CollectionArtReadDto of(Art art) {
        this.artId = art.getId();
        this.name = art.getName();
        this.register = art.getRegister();
        this.cherryNum = art.getCherryNum();
        this.filePath = art.getImageList().get(0).getFilePath();
        return this;
    }
}
