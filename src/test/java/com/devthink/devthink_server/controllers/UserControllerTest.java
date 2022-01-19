package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.UserModificationData;
import com.devthink.devthink_server.dto.UserRegistrationData;
import com.devthink.devthink_server.errors.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;
    private Long NOT_EXISTED_ID = 100L;

    @BeforeEach
    void setUp() {
        given(userService.registerUser(any(UserRegistrationData.class))).will(invocation -> {
            UserRegistrationData userRegistrationData = invocation.getArgument(0);

            return User.builder()
                    .id(13L)
                    .email(userRegistrationData.getEmail())
                    .nickname(userRegistrationData.getNickname())
                    .role(userRegistrationData.getRole())
                    .build();
        });

        given(userService.updateUser(eq(1L), any(UserModificationData.class)))
                .will(invocation -> {
                    Long id = invocation.getArgument(0);
                    UserModificationData modificationData = invocation.getArgument(1);
                    return User.builder()
                            .id(id)
                            .nickname(modificationData.getNickname())
                            .email("tester@email.com")
                            .role(modificationData.getRole())
                            .build();
                });

        given(userService.updateUser(eq(NOT_EXISTED_ID), any(UserModificationData.class)))
                .willThrow(new UserNotFoundException(NOT_EXISTED_ID));

        given(userService.deleteUser(NOT_EXISTED_ID))
                .willThrow(new UserNotFoundException(NOT_EXISTED_ID));
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

    @Test
    void 올바른_회원정보로_회원정보를_수정하는_경우() throws Exception {
        mvc.perform(
                        patch("/users/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"nickname\":\"newTest\",\"role\":\"senior\"}")
                )
                .andExpect(content().string(containsString("newTest")))
                .andExpect(content().string(containsString("senior")))
                .andExpect(status().isOk());

        verify(userService).updateUser(eq(1L), any(UserModificationData.class));
    }


    @Test
    void 올바르지_않은_회원정보로_회원정보를_수정하는_경우() throws Exception {
        mvc.perform(
                        patch("/users/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"nickname\":\"\",\"role\":\"\"}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void 존재하지_않은_회원의_정보를_수정하는_경우() throws Exception {
        mvc.perform(
                        patch("/users/100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"nickname\":\"TEST\",\"role\":\"TEST\"}")
                )
                .andExpect(status().isNotFound());

        verify(userService).updateUser(eq(NOT_EXISTED_ID), any(UserModificationData.class));
    }

    @Test
    void 존재하는_회원을_삭제하는_경우() throws Exception {
        mvc.perform(
                delete("/users/1"))
                .andExpect(status().isOk());

        verify(userService).deleteUser(1L);
    }

    @Test
    void 존재하지_않는_회원을_삭제하는_경우() throws Exception {
        mvc.perform(delete("/users/100"))
                .andExpect(status().isNotFound());

        verify(userService).deleteUser(NOT_EXISTED_ID);
    }
}

