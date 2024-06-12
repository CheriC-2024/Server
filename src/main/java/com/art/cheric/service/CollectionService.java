package com.art.cheric.service;

import com.art.cheric.dto.collection.request.CollectionCreationRequestDto;
import com.art.cheric.dto.collection.respond.CollectionReadDto;
import com.art.cheric.entity.Art;
import com.art.cheric.entity.Collection;
import com.art.cheric.entity.CollectionArt;
import com.art.cheric.entity.User;
import com.art.cheric.exception.DuplicateEntryException;
import com.art.cheric.repository.ArtRepository;
import com.art.cheric.repository.CollectionArtRepository;
import com.art.cheric.repository.CollectionRepository;
import com.art.cheric.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CollectionService {

    private final UserRepository userRepository;
    private final ArtRepository artRepository;
    private final CollectionRepository collectionRepository;
    private final CollectionArtRepository collectionArtRepository;

    // 컬렉션만 생성하는 api
    public Long createNewCollection(CollectionCreationRequestDto collectionCreationRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 ID 없음 : " + userId));

        Collection collection = Collection.builder()
                .name(collectionCreationRequestDto.getName())
                .description(collectionCreationRequestDto.getDescription())
                .user(user)
                .build();

        collectionRepository.save(collection);

        return collection.getId();
    }

    // 컬렉션에 작품 추가하는 api
    public void addNewArt(Long artId, Long collectionId) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new IllegalArgumentException("컬렉션 ID 없음 : " + collectionId));

        Art art = artRepository.findById(artId)
                .orElseThrow(() -> new IllegalArgumentException("작품 ID 없음 : " + artId));


        if (collectionArtRepository.findByArtIdAndCollectionId(artId, collectionId).isPresent()) {
            throw new DuplicateEntryException(collectionId + "에 이미 " + artId + " 작품이 존재합니다.");
        }

        CollectionArt collectionArt = CollectionArt.builder()
                .collection(collection)
                .art(art)
                .build();

        collectionArtRepository.save(collectionArt);
    }

    public List<CollectionReadDto> getCollectionsByIds(List<Long> ids) {
        List<Collection> collections = collectionRepository.findByIdIn(ids);
        return collections.stream()
                .map(collection -> new CollectionReadDto().of(collection))
                .collect(Collectors.toList());
    }
}
