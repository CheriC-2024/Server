package com.art.cheric.repository;

import com.art.cheric.entity.Art;
import com.art.cheric.entity.ArtistArt;
import com.art.cheric.entity.MyArt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyArtRepository extends JpaRepository<MyArt, Long> {
    MyArt findByArt(Art art);

}