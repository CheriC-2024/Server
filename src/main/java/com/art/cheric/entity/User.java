package com.art.cheric.entity;

import com.art.cheric.constant.Role;
import com.art.cheric.dto.UserCreateRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "User")
@NoArgsConstructor
@Getter
public class User extends BaseTime {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    @Comment("사용자 이메일")
    private String email;

    @Column(name = "name", length = 10, nullable = false)
    @Comment("사용자 이름")
    private String name;

    @Column(name = "profileImg", length = 500, nullable = false)
    @Comment("사용자 이미지")
    private String profileImg;

    @Column(name = "profileImgPath", length = 500, nullable = false)
    @Comment("사용자 이미지 경로")
    private String profileImgPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Comment("사용자 권한")
    private Role role;

    // TODO: 체리 구매까지 확장되면 어떻게 할지 더 고민하기
    @Column(name = "cherry", nullable = false)
    @Comment("보유 체리 개수")
    @ColumnDefault("0")
    private int cherry;

    @Builder
    public User(String email, String name, String profileImg, String profileImgPath, Role role, int cherry) {
        this.email = email;
        this.name = name;
        this.profileImg = profileImg;
        this.profileImgPath = profileImgPath;
        this.role = role;
        this.cherry = cherry;
    }

    public static User createUser(UserCreateRequestDto userCreateRequestDto) {
        return User.builder()
                .name(userCreateRequestDto.getName())
                .email(userCreateRequestDto.getEmail())
                .profileImg(userCreateRequestDto.getProfileImg())
                .profileImgPath(userCreateRequestDto.getProfileImgPath())
                .role(userCreateRequestDto.getRole())
                .cherry(userCreateRequestDto.getCherry())
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User user)) return false;

        return Objects.equals(this.email, user.getEmail()) &&
                Objects.equals(this.name, user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name);
    }
}
