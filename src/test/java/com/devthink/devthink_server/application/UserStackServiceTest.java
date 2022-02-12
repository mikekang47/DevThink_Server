package com.devthink.devthink_server.application;


import com.devthink.devthink_server.domain.Stack;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.UserStack;
import com.devthink.devthink_server.infra.StackRepository;
import com.devthink.devthink_server.infra.UserRepository;
import com.devthink.devthink_server.infra.UserStackRepository;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserStackServiceTest {
    private UserStackService userStackService;
    private StackService stackService;
    private UserService userService;

    private final StackRepository stackRepository =
            mock(StackRepository.class);

    private final UserStackRepository userStackRepository =
            mock(UserStackRepository.class);

    private final UserRepository userRepository =
            mock(UserRepository.class);

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        userStackService = new UserStackService(mapper, userStackRepository, userRepository, stackRepository);

        User user = User.builder()
                .id(1L)
                .email("test@email.com")
                .password("1234567890")
                .nickname("test")
                .name("tester")
                .role("junior")
                .build();

        Stack stack = Stack.builder()
                .name("Java")
                .build();

        UserStack userStack = UserStack.builder().user(user).stack(stack).build();

        given(userStackRepository.findAllByUserId(1L)).willReturn(List.of(userStack));

    }

    @Test
    void 유저스택을_모두_불러오는_경우() {
        Long userId = 1L;
        List<UserStack> stacks = userStackService.getUserStacks(userId);

        assertThat(stacks).isNotEmpty();

        User user = stacks.get(0).getUser();
        assertThat(user.getEmail()).isEqualTo("test@email.com");
    }

}
