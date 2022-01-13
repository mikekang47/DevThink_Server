package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.UserRepository;
import com.devthink.devthink_server.dto.UserRegistrationData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class UserServiceTest {
    private final String EXISTED_EMAIL = "exited@email.com";

    private UserService userService;

    private final UserRepository userRepository = mock(UserRepository.class);


    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        userService = new UserService(userRepository, mapper);



        given(userRepository.save(any(User.class))).will(invocation -> {
            User user = User.builder()
                    .id(13L)
                    .email(EXISTED_EMAIL)
                    .nickname("Test")
                    .role("senior")
                    .build();

            return user;
        });

        

    }

    @Test
    void 올바른_정보로_회원가입을_하려는_경우() {
        UserRegistrationData registrationData = UserRegistrationData.builder()
                .nickname("tester")
                .email("test@email.com")
                .build();

        User user = userService.registerUser(registrationData);

        assertThat(user.getId()).isEqualTo(13L);
        assertThat(user.getNickname()).isEqualTo("Test");

        verify(userRepository).save(any(User.class));
    }

}
