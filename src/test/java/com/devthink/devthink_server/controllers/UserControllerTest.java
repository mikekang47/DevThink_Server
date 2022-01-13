package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.UserRegistrationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        given(userService.registerUser(any(UserRegistrationData.class))).will(invocation -> {
            UserRegistrationData userRegistrationData = invocation.getArgument(0);

            return User.builder()
                    .email(userRegistrationData.getEmail())
                    .nickname(userRegistrationData.getNickname())
                    .role(userRegistrationData.getRole())
                    .build();
        });


    }

    @Test
    void 올바른_회원정보로_회원가입을_하려는_경우() throws Exception {
        mvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"tester@email.com\"," +
                                "\"nickname\":\"Tester\",\"role\":\"senior\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("\"email\":\"tester@email.com\"")))
                .andExpect(content().string(containsString("Test")))
                .andExpect(content().string(containsString("senior")));

        verify(userService).registerUser(any(UserRegistrationData.class));
    }

    @Test
    void 올바르지_않은_회원정보로_회원가입을_하려는_경우() throws Exception {
        mvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        )
                .andExpect(status().isBadRequest());
    }



}
