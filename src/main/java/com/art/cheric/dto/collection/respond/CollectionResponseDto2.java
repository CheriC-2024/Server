package com.art.cheric.dto.collection.respond;

import lombok.Getter;

import java.util.List;

// TODO 나중에 수정하기
@Getter
public class CollectionResponseDto2 {
    private List<CollectionReadAllDto> collections;

    public CollectionResponseDto2 of(List<CollectionReadAllDto> collections) {
        this.collections = collections;
        return this;
    }
}
