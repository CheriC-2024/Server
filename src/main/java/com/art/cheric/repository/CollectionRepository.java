package com.art.cheric.repository;

import com.art.cheric.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    List<Collection> findByIdIn(List<Long> ids);
}
