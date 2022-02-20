package com.devthink.devthink_server.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

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

        user.changePassword("TEST");

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
    void 인증() {
        User user = User.builder()
                .password("test")
                .deleted(true)
                .build();

        assertThat(user.authenticate("test")).isFalse();
        assertThat(user.authenticate("xxx")).isFalse();

    }
}
