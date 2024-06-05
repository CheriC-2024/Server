package com.art.cheric.service;

import com.art.cheric.constant.Role;
import com.art.cheric.dto.CollectionCreationRequestDto;
import com.art.cheric.entity.Art;
import com.art.cheric.entity.Collection;
import com.art.cheric.entity.CollectionArt;
import com.art.cheric.entity.User;
import com.art.cheric.repository.ArtRepository;
import com.art.cheric.repository.CollectionRepository;
import com.art.cheric.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CollectionService {

    private final UserRepository userRepository;
    private final ArtRepository artRepository;
    private final CollectionRepository collectionRepository;

    // 컬렉션만 생성하는 api
    public Long createNewCollection(CollectionCreationRequestDto collectionCreationRequestDto, String email) {
        User user = userRepository.findByEmail(email);

        // TODO: 회원가입 플로우 생기면 변경하기
        if (user == null) {
            User newUser = User.builder()
                    .name("test User")
                    .email(email)
                    .profileImg("프로필 사진")
                    .profileImgPath("")
                    .role(Role.COLLECTOR)
                    .cherry(5)
                    .build();
            user = userRepository.save(newUser);
        }

        Collection collection = Collection.builder()
                .name(collectionCreationRequestDto.getName())
                .description(collectionCreationRequestDto.getDescription())
                .user(user)
                .build();

        collectionRepository.save(collection);

        return collection.getId();
    }

    // 컬렉션에 작품 추가하는 api
    public void addNewArt(Long artId, Long collectionId, String email){
        User user = userRepository.findByEmail(email);

        // TODO: 회원가입 플로우 생기면 변경하기
        if (user == null) {
            User newUser = User.builder()
                    .name("test User")
                    .email(email)
                    .profileImg("프로필 사진")
                    .profileImgPath("")
                    .role(Role.COLLECTOR)
                    .cherry(5)
                    .build();
            user = userRepository.save(newUser);
        }

        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new IllegalArgumentException("컬렉션 ID 없음 : " + collectionId));

        Art art = artRepository.findById(artId)
                .orElseThrow(() -> new IllegalArgumentException("작품 ID 없음 : " + artId));

        CollectionArt collectionArt = CollectionArt.builder()
                .collection(collection)
                .art(art)
                .build();

        collection.addCollectionArt(collectionArt);

        collectionRepository.save(collection);
    }
}
