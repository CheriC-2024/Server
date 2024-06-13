package com.art.cheric.dto.collection.respond;

import com.art.cheric.entity.Collection;
import lombok.Getter;

@Getter
public class CollectionReadAllDto {
    private Long id;
    private String name;
    private String description;

    public CollectionReadAllDto of(Collection collection) {
        this.id = collection.getId();
        this.description = collection.getDescription();
        this.name = collection.getName();
        return this;
    }
}
