package com.art.cheric.service;

import com.art.cheric.dto.art.request.ArtistArtCreateRequestDto;
import com.art.cheric.dto.art.request.MyArtCreateRequestDto;
import com.art.cheric.entity.*;
import com.art.cheric.repository.ArtRepository;
import com.art.cheric.repository.ArtistArtRepository;
import com.art.cheric.repository.MyArtRepository;
import com.art.cheric.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtService {

    @Value("${uploadPath}")
    private String uploadPath;

    private final ArtRepository artRepository;
    private final ArtistArtRepository artistArtRepository;
    private final MyArtRepository myArtRepository;
    private final UserRepository userRepository;


    // 작품 사진 1개 저장
    public Long saveArtistArt(
            ArtistArtCreateRequestDto artistArtCreateRequestDto,
            MultipartFile artImg,
            Long userId
    ) throws Exception {

        // 사용자 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 ID 없음 : " + userId));

        // 작가 작품 생성
        ArtistArt artistArt = artistArtCreateRequestDto.createArtistArt(user);

        // 이미지 파일로 받아와서 지정 경로에 저장
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + artImg.getOriginalFilename();
        File artImgFile = new File(uploadPath, fileName);
        artImg.transferTo(artImgFile);

        // image Entity 생성 및 저장
        Image image = Image.builder()
                .art(artistArt.getArt())
                .file(fileName)
                .filePath(uploadPath + "/" + fileName)
                .fileSize(artImg.getSize())
                .build();

        // 작품 저장
        artistArt.getArt().addImage(image);

        Art art = artRepository.save(artistArt.getArt());

        // 작가 작품 저장
        artistArtRepository.save(artistArt);

        // 작가 작품 id 반환
        return art.getId();
    }

    // 작가 작품 조회 api
    public ArtistArtCreateRequestDto getArtistArtById(Long artId) {
        Art art = artRepository.findById(artId)
                .orElseThrow(() -> new IllegalArgumentException("작품 ID 없음 : " + artId));

        Optional<ArtistArt> artistArtOptional = Optional.ofNullable(artistArtRepository.findByArt(art));
        if (artistArtOptional.isPresent()) {
            ArtistArt artistArt = artistArtOptional.get();
            ArtistArtCreateRequestDto artistArtCreateRequestDto = new ArtistArtCreateRequestDto();
            return artistArtCreateRequestDto.of(artistArt);
        } else {
            throw new IllegalArgumentException("해당 작품은 소장 작품입니다.");
        }
    }

    // 작품 사진 1개 저장
    public Long saveMyArt(
            MyArtCreateRequestDto myArtCreateRequestDto,
            MultipartFile artImg,
            Long userId
    ) throws Exception {
        // 사용자 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 ID 없음 : " + userId));

        // 작품 생성
        MyArt myArt = myArtCreateRequestDto.createMyArt(user);

        // 이미지 파일로 받아와서 지정 경로에 저장
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + artImg.getOriginalFilename();
        File artImgFile = new File(uploadPath, fileName);
        artImg.transferTo(artImgFile);

        // image Entity 생성 및 저장
        Image image = Image.builder()
                .art(myArt.getArt())
                .file(fileName)
                .filePath(uploadPath + "/" + fileName)
                .fileSize(artImg.getSize())
                .build();

        // 작품 저장
        myArt.getArt().addImage(image);
        Art art = artRepository.save(myArt.getArt());

        // 소장 작품 저장
        myArtRepository.save(myArt);

        // 작품 id 반환
        return art.getId();
    }

    // 작가 작품 조회 api
    public MyArtCreateRequestDto getMyArtById(Long artId) {
        Art art = artRepository.findById(artId)
                .orElseThrow(() -> new IllegalArgumentException("작품 ID 없음 : " + artId));

        Optional<MyArt> myArtOptional = Optional.ofNullable(myArtRepository.findByArt(art));
        if (myArtOptional.isPresent()) {
            MyArt myArt = myArtOptional.get();
            MyArtCreateRequestDto myArtCreateRequestDto = new MyArtCreateRequestDto();
            return myArtCreateRequestDto.of(myArt);
        } else {
            throw new IllegalArgumentException("해당 작품은 소장 작품입니다.");
        }
    }

    // TODO: 작품 사진 다수 저장 로직

    // TODO: 작품 삭제, 작품 수정, 작품 조회
}
