package com.art.cheric.repository;

import com.art.cheric.entity.CollectionArt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionArtRepository extends JpaRepository<CollectionArt, Long> {
    Optional<CollectionArt> findByArtIdAndCollectionId(Long artId, Long collectionId);

}
