package com.art.cheric.repository;

import com.art.cheric.entity.Art;
import com.art.cheric.entity.ArtistArt;
import com.art.cheric.entity.MyArt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistArtRepository extends JpaRepository<ArtistArt, Long> {
    ArtistArt findByArt(Art art);

}