package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.AuthenticationService;
import com.devthink.devthink_server.application.StackService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.application.UserStackService;
import com.devthink.devthink_server.domain.Stack;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.UserStack;
import com.devthink.devthink_server.dto.StackData;
import com.devthink.devthink_server.dto.UserStackData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserStackController.class)
class UserStackControllerTest {
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDk";

    @Autowired
    MockMvc mvc;

    @MockBean
    UserStackService userStackService;

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    UserService userService;

    @MockBean
    StackService stackService;

    @BeforeEach
    void setUp() {
        User user = User.builder().id(1L)
                .email("tester@example.com")
                .name("TEST")
                .nickname("tester")
                .phoneNum("01012341234")
                .password("1234567890")
                .role("senior")
                .build();

        Stack stack = new Stack(1L,"C/C++");

        UserStack userStack = UserStack.builder().user(user).stack(stack).build();

        
        given(userStackService.getUserStacks(1L)).willReturn(List.of(userStack));

//        given(userStackService.create(eq(1L), any(UserStackData.class))).willReturn(userStack);

        given(authenticationService.parseToken(VALID_TOKEN)).willReturn(1L);

    }

    @Test
    void 특정_사용자스택을_조회하는_경우() throws Exception {
        mvc.perform(get("/users/stacks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"1\", \"stackId\":1\"}")
                )
                .andExpect(status().isOk());
    }

//    @Test
//    void 올바른_정보로_사용자스택을_생성하는_경우() throws Exception  {
//        mvc.perform(post("/users/stacks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization","Bearer "+VALID_TOKEN)
//
//                )
//                .andExpect(status().isCreated());
//    }

}
