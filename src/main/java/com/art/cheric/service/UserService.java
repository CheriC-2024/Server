package com.art.cheric.service;

import com.art.cheric.constant.Role;
import com.art.cheric.dto.user.request.UserCreateRequestDto;
import com.art.cheric.dto.user.respond.CherryResponseDto;
import com.art.cheric.entity.User;
import com.art.cheric.exception.OutOfRangeException;
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
public class UserService {

    @Value("${uploadPath}")
    private String uploadPath;

    final private UserRepository userRepository;

    public Long saveUser(UserCreateRequestDto userCreateRequestDto,
                         MultipartFile profileImg) throws Exception {
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + profileImg.getOriginalFilename();
        File itemImgFile = new File(uploadPath, fileName);
        profileImg.transferTo(itemImgFile);

        User user = userCreateRequestDto.createUser(fileName, uploadPath + "/" + fileName);
        validateDuplicateUser(user);

        return userRepository.save(user).getId();
    }

    private void validateDuplicateUser(User user) {
        Optional<User> findUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));

        if (findUser.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    public Role updateRole(Long userId, Role role) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 ID 없음 : " + userId));

        user.updateRole(role);

        userRepository.save(user);

        return user.getRole();
    }

    public CherryResponseDto readCherry(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 ID 없음 : " + userId));

        CherryResponseDto cherryResponseDto = new CherryResponseDto();
        cherryResponseDto.setCherry(user.getCherry());
        return cherryResponseDto;
    }

    public int plusCherry(Long userId, int plusCherryNum) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 ID 없음 : " + userId));

        user.plusCherry(plusCherryNum);

        userRepository.save(user);

        return user.getCherry();
    }

    public int minusCherry(Long userId, int minusCherryNum) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 ID 없음 : " + userId));

        user.minusCherry(minusCherryNum);

        if (user.getCherry() < 0) {
            throw new OutOfRangeException("체리 수는 0보다 작게 설정할 수 없습니다.");
        }

        userRepository.save(user);

        return user.getCherry();
    }
}
