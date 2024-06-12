package com.art.cheric.dto.collection.respond;

import lombok.Getter;

import java.util.List;

@Getter
public class CollectionResponseDto {
    private List<CollectionReadDto> collections;

    public CollectionResponseDto of(List<CollectionReadDto> collections) {
        this.collections = collections;
        return this;
    }
}
