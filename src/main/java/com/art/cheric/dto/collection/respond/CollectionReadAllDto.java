package com.art.cheric.dto.collection.respond;

import com.art.cheric.entity.Collection;
import lombok.Getter;

@Getter
public class CollectionReadAllDto {
    private Long id;
    private String name;
    private String description;
    private String filePath;

    public CollectionReadAllDto of(Collection collection) {
        this.id = collection.getId();
        this.description = collection.getDescription();
        this.name = collection.getName();
        if(!collection.getCollectionArtList().isEmpty()){
            this.filePath = collection.getCollectionArtList().get(0).getArt().getImageList().get(0).getFilePath();
        }
        return this;
    }
}
