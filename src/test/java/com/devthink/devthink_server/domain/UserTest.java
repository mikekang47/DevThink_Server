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
                        .build()
        );

        assertThat(user.getNickname()).isEqualTo("Test");
        assertThat(user.getRole()).isEqualTo("senior");
    }

    @Test
    void 삭제() {
        User user = User.builder().build();

        user.destroy();

        assertThat(user.isDeleted()).isTrue();
    }
    
}
