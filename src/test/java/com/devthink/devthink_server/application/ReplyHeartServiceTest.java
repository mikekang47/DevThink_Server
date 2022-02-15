package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Reply;
import com.devthink.devthink_server.domain.ReplyHeart;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.infra.ReplyHeartRepository;
import com.devthink.devthink_server.infra.ReplyRepository;
import com.devthink.devthink_server.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ReplyHeartServiceTest {

    private ReplyHeartService replyHeartService;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final ReplyRepository replyRepository = mock(ReplyRepository.class);
    private final ReplyHeartRepository replyHeartRepository = mock(ReplyHeartRepository.class);

    @BeforeEach
    void setUp() {
        replyHeartService = new ReplyHeartService(userRepository,replyRepository, replyHeartRepository);


    }

    @Test
    void create() {
        User user = User.builder().id(1L).build();
        Reply reply = Reply.builder().id(2L).build();

        ReplyHeart replyHeart = ReplyHeart.builder()
                .id(1L)
                .user(user)
                .reply(reply)
                .build();

        assertThat(replyHeart).isNotNull();


    }
}
