package com.art.cheric.entity;

import com.art.cheric.constant.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(name = "profileImgPath", length = 500, nullable = false)
    @Comment("사용자 이미지")
    private String profileImgPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Comment("사용자 권한")
    private Role role;

    // TODO: 체리 구매까지 확장되면 어떻게 할지 더 고민하기
    @Column(name = "cherry", nullable = false)
    @Comment("체리 개수")
    private int cherry;

    @Builder
    public User(String email, String name, String profileImgPath, Role role) {
        this.email = email;
        this.name = name;
        this.profileImgPath = profileImgPath;
        this.role = role;
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
