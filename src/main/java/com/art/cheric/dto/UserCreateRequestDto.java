package com.art.cheric.dto;

import com.art.cheric.constant.Role;
import com.art.cheric.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {
    private String name;
    private String email;
    private String profileImg;
    private String profileImgPath;
    private Role role;
    private int cherry;

    @Builder
    public UserCreateRequestDto(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImg = user.getProfileImg();
        this.profileImgPath = user.getProfileImgPath();
        this.role = user.getRole();
        this.cherry = user.getCherry();
    }
}
