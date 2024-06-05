package com.art.cheric.dto;

import com.art.cheric.entity.Image;
import lombok.Getter;

@Getter
public class ImageCreateRequestDto {
    private String file;
    private String filePath;
    private Long fileSize;

    // TODO: 추후 modelmapper class 로 변경하기 > builder 로 하니까 mapping이 제대로 되지 않나봐..
    public Image createImage(){
        return Image.builder()
                .file(this.file)
                .filePath(this.filePath)
                .fileSize(this.fileSize)
                .build();
    }

    public ImageCreateRequestDto of(Image image){
        this.file = image.getFile();
        this.filePath = image.getFilePath();
        this.fileSize = image.getFileSize();
        return this;
    }
}
