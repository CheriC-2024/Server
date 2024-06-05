package com.art.cheric.service;

import com.art.cheric.dto.ArtCreateRequestDto;
import com.art.cheric.entity.Art;
import com.art.cheric.entity.Image;
import com.art.cheric.repository.ArtRepository;
import com.art.cheric.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtService {

    @Value("${uploadPath}")
    private String uploadPath;

    private final ArtRepository artRepository;
    private final ImageRepository imageRepository;

    // 작품 사진 1개 저장
    public Long saveArt(ArtCreateRequestDto artCreateRequestDto,
                        MultipartFile itemImg) throws Exception {
        // 작품 생성
        Art art = artCreateRequestDto.createArt();

        // 이미지 파일로 받아와서 지정 경로에 저장
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + itemImg.getOriginalFilename();
        File itemImgFile = new File(uploadPath, fileName);
        itemImg.transferTo(itemImgFile);

        // image Entity 생성 및 저장
        Image image = Image.builder()
                .art(art)
                .file(fileName)
                .filePath(uploadPath + "/" + fileName)
                .fileSize(itemImg.getSize())
                .build();

        imageRepository.save(image);

        // 작품 저장
        art.addImage(image);
        artRepository.save(art);

        // 작품 저장된 id 반환
        return art.getId();
    }

    // 작품 조회 api
    public ArtCreateRequestDto getArtById(Long artId){
        Art art = artRepository.findById(artId)
                .orElseThrow(() -> new IllegalArgumentException("작품 ID 없음 : " + artId));

        // TODO: 추후 ModelMapper 맞게 수정하기
        ArtCreateRequestDto artCreateRequestDto = new ArtCreateRequestDto();

        return artCreateRequestDto.of(art);
    }

    // TODO: 작품 사진 다수 저장 로직

    // TODO: 작품 삭제, 작품 수정, 작품 조회
}
