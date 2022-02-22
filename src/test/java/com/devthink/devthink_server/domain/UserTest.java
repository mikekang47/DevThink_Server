package com.devthink.devthink_server.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void changeWith() {
        User user = User.builder().build();

        user.changeWith(
                User.builder()
                        .nickname("Test")
                        .role("senior")
                        .password("TEST")
                        .build()
        );

        assertThat(user.getNickname()).isEqualTo("Test");
        assertThat(user.getRole()).isEqualTo("senior");
        assertThat(user.getPassword()).isNotEqualTo("");
    }

    @Test
    void 비밀번호_변경() {
        User user = User.builder().build();

        user.changePassword("TEST", passwordEncoder);

        assertThat(user.getPassword()).isNotEmpty();
        assertThat(user.getPassword()).isNotEqualTo("TEST");
    }

    @Test
    void 삭제() {
        User user = User.builder()
                .build();

        user.destroy();

        assertThat(user.isDeleted()).isTrue();
    }

    @Test
    void 존재하는_사용자_인증() {
        User user = User.builder().build();
        user.changePassword("test", passwordEncoder);

        assertThat(user.authenticate("test", passwordEncoder)).isTrue();
        assertThat(user.authenticate("xxx", passwordEncoder)).isFalse();

    }




}
