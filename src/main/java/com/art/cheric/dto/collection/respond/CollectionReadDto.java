package com.art.cheric.dto.collection.respond;

import com.art.cheric.entity.Collection;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CollectionReadDto {
    private Long collectionId;
    private String collectionName;
    private List<CollectionArtReadDto> artList;

    public CollectionReadDto of(Collection collection) {
        this.collectionName = collection.getName();
        this.collectionId = collection.getId();
        this.artList = collection.getCollectionArtList().stream()
                .map(collectionArt -> new CollectionArtReadDto().of(collectionArt.getArt()))
                .collect(Collectors.toList());
        return this;
    }
}