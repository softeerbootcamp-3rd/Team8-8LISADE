package com.lisade.togeduck.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.exception.UserNotFoundException;
import com.lisade.togeduck.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자가 DB에 잘 저장되는지 확인")
    void saveUser() {
        //given
        SignUpDto signUpDto = SignUpDto.builder()
            .userId("testId")
            .password("password12@")
            .nickname("nickname")
            .email("right@email.com")
            .build();
        User user = UserMapper.toUser(signUpDto);
        //when
        User savedUser = userRepository.save(user);

        //then
        assertThat(savedUser.getUserId()).isEqualTo(user.getUserId());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(savedUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("DB에 저장된 사용자 정보가 제대로 조회되는지 확인")
    void existUser() {
        //given
        SignUpDto signUpDto = SignUpDto.builder()
            .userId("testId")
            .password("password12@")
            .nickname("nickname")
            .email("right@email.com")
            .build();
        User user = UserMapper.toUser(signUpDto);

        //when
        userRepository.save(user);
        boolean existId = userRepository.existsByUserId(signUpDto.getUserId());
        boolean existNickname = userRepository.existsByNickname(signUpDto.getNickname());
        boolean existEmail = userRepository.existsByEmail(signUpDto.getEmail());

        //then
        assertThat(existId).isTrue();
        assertThat(existNickname).isTrue();
        assertThat(existEmail).isTrue();
    }

    @Test
    @DisplayName("로그인할 때 사용자 정보가 잘 조회되는지 확인")
    void login() {
        //given
        String userId = "testId";
        String password = "password12@";

        SignUpDto signUpDto = SignUpDto.builder()
            .userId(userId)
            .password(password)
            .nickname("nickname")
            .email("right@email.com")
            .build();
        User user = UserMapper.toUser(signUpDto);
        userRepository.save(user);

        //when
        User findUser = userRepository.findByUserIdAndPassword(userId, password)
            .orElseThrow(UserNotFoundException::new);

        //then
        assertThat(findUser.getUserId()).isEqualTo(userId);
        assertThat(findUser.getPassword()).isEqualTo(password);
        assertThat(findUser.getNickname()).isEqualTo("nickname");
        assertThat(findUser.getEmail()).isEqualTo("right@email.com");
    }

    @DisplayName("잘못된 정보로 로그인할 때 UserNotFoundException 발생하는지 확인")
    void fail_login() {
        //given
        String userId = "testId";
        String password = "password";

        SignUpDto signUpDto = SignUpDto.builder()
            .userId(userId)
            .password(password)
            .nickname("nickname")
            .email("right@email.com")
            .build();
        User user = UserMapper.toUser(signUpDto);
        userRepository.save(user);

        //when, then
        Assertions.assertThatThrownBy(() -> userRepository.findByUserIdAndPassword(userId, password)
                .orElseThrow(UserNotFoundException::new))
            .isEqualTo(UserNotFoundException.class);
    }
}