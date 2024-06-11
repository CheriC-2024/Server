package com.art.cheric.repository;

import com.art.cheric.constant.PartSort;
import com.art.cheric.constant.Role;
import com.art.cheric.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입 테스트")
    public void createMemberTest() throws Exception {
        // given
        String name = "name";
        String email = "name@test.com";
        String profileImg = "";
        String profileImgPath = "";
        PartSort partSort = PartSort.DIGITAL_ART;
        boolean isAlreadyCollector = false;

        User user = User.builder()
                .name(name)
                .email(email)
                .profileImg(profileImg)
                .profileImgPath(profileImgPath)
                .partSort(partSort)
                .isAlreadyCollector(isAlreadyCollector)
                .build();

        // when
        Long id = userRepository.save(user).getId();

        // then
        User findUser = userRepository.findById(id).orElseThrow(() -> new Exception("Null"));

        assertThat(findUser.getEmail()).as("User 정보가 제대로 저장되지 않음.").isEqualTo(email);
        assertThat(findUser.getRegTime()).as("Auditing 기능이 제대로 작동하지 않음.").isNotNull();

        System.out.println(findUser.getEmail());
        System.out.println(findUser.getCherry());
        System.out.println(findUser.getRole());
        System.out.println(findUser.getRegTime());
    }

}
