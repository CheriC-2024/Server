package com.art.cheric.controller;

import com.art.cheric.constant.Role;
import com.art.cheric.dto.user.request.UserCreateRequestDto;
import com.art.cheric.dto.user.respond.CherryResponseDto;
import com.art.cheric.exception.OutOfRangeException;
import com.art.cheric.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    // 사용자 등록 api
    @PostMapping
    public ResponseEntity<String> createNewCollection(@Valid @RequestPart(name = "user") UserCreateRequestDto userCreateRequestDto,
                                                      @RequestPart(name = "profileImg") MultipartFile profileImg) {
        try {
            Long userId = userService.saveUser(userCreateRequestDto, profileImg);

            return ResponseEntity.ok("사용자 ID: " + userId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 생성 실패 : " + e.getMessage());
        }
    }

    // 사용자 권한 변경 api
    @PatchMapping
    public ResponseEntity<String> updateRole(@RequestParam(name = "userId") Long userId,
                                             @RequestParam(name = "role") Role role) {
        try {
            Role updateRole = userService.updateRole(userId, role);

            return ResponseEntity.ok("권한이 " + updateRole + "로 업데이트 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 생성 실패 : " + e.getMessage());
        }
    }

    @GetMapping("/cherry")
    public ResponseEntity<CherryResponseDto> readCherry(@RequestParam(name = "userId") Long userId) {
        try {
            CherryResponseDto cherryResponseDto = userService.readCherry(userId);

            return ResponseEntity.ok(cherryResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/cherry/minus")
    public ResponseEntity<String> minusCherry(@RequestParam(name = "userId") Long userId,
                                              @RequestParam(name = "minusCherryNum") int minusCherryNum) {
        try {
            int cherry = userService.minusCherry(userId, minusCherryNum);

            return ResponseEntity.ok("체리 개수가 " + cherry + "로 감소되었습니다.");
        } catch (OutOfRangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("체리 감소 실패 : " + e.getMessage());
        }
    }

    @PatchMapping("/cherry/plus")
    public ResponseEntity<String> plusCherry(@RequestParam(name = "userId") Long userId,
                                             @RequestParam(name = "plusCherryNum") int plusCherryNum) {
        try {
            int cherry = userService.plusCherry(userId, plusCherryNum);

            return ResponseEntity.ok("체리 개수가 " + cherry + "로 증가되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("체리 증가 실패 : " + e.getMessage());
        }
    }
}
