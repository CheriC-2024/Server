package com.art.cheric.dto.collection.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CollectionReadRequestDto {
    private List<Long> collectionIds;
}
