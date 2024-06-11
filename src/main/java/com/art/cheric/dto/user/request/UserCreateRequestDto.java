package com.art.cheric.dto.user.request;

import com.art.cheric.constant.PartSort;
import com.art.cheric.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Length(min = 2, max = 20, message = "이름은 2자 이상, 20자 이하로 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotNull(message = "선호 장르는 필수 선택 값입니다.")
    private PartSort partSort;

    @NotNull(message = "컬렉터 경험 여부 필수 선택 값입니다.")
    private Boolean isAlreadyCollector;

    public User createUser(String profileImg, String profileImgPath) {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .profileImg(profileImg)
                .profileImgPath(profileImgPath)
                .partSort(this.partSort)
                .isAlreadyCollector(this.isAlreadyCollector)
                .build();
    }

    public UserCreateRequestDto of(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.partSort = user.getPartSort();
        this.isAlreadyCollector = user.getIsAlreadyCollector();

        return this;

    }
}
