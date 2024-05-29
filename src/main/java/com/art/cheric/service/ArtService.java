package com.art.cheric.service;

import com.art.cheric.dto.ArtCreateRequestDto;
import com.art.cheric.entity.Art;
import com.art.cheric.entity.Image;
import com.art.cheric.repository.ArtRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final ArtRepository artRepository;

    // 작품 사진 1개 저장
    public Long saveArt(ArtCreateRequestDto artCreateRequestDto,
                        MultipartFile itemImg) throws Exception {

        Art art = artCreateRequestDto.createArt();

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + itemImg.getOriginalFilename();
        File itemImgFile = new File(uploadPath, fileName);
        itemImg.transferTo(itemImgFile);


        Image image = Image.builder()
                .art(art)
                .file(fileName)
                .filePath(uploadPath + "/" + fileName)
                .fileSize(itemImg.getSize())
                .build();

        art = art.addImage(image);

        artRepository.save(art);

        return art.getId();
    }

    // TODO: 작품 사진 다수 저장 로직
}
