package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Reply;
import com.devthink.devthink_server.domain.ReplyHeart;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReplyRequestData;
import com.devthink.devthink_server.dto.UserRegistrationData;
import com.devthink.devthink_server.errors.ReplyNotFoundException;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.devthink.devthink_server.infra.ReplyHeartRepository;
import com.devthink.devthink_server.infra.ReplyRepository;
import com.devthink.devthink_server.infra.UserRepository;
import com.devthink.devthink_server.utils.JwtUtil;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ReplyHeartServiceTest {
    private final String EXISTED_EMAIL = "exited@email.com";

    private ReplyHeartService replyHeartService;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final ReplyRepository replyRepository = mock(ReplyRepository.class);
    private final ReplyHeartRepository replyHeartRepository = mock(ReplyHeartRepository.class);
    private UserService userService;


    @BeforeEach
    void setUp() {
        replyHeartService = new ReplyHeartService(userRepository,replyRepository, replyHeartRepository);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        userService = new UserService(userRepository, mapper, passwordEncoder);

        given(userRepository.save(any(User.class))).will(invocation -> {
            User user = User.builder()
                    .id(13L)
                    .email(EXISTED_EMAIL)
                    .nickname("Test")
                    .role("senior")
                    .build();

            return user;
        });

        given(replyRepository.save(any(Reply.class))).will(invocation -> {
            Reply reply = Reply.builder()
                    .id(10L)
                    .content("good")
                    .build();
            return reply;
        });

    }
    
}
