package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.AuthenticationService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.UserModificationData;
import com.devthink.devthink_server.dto.UserRegistrationData;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.github.dozermapper.core.Mapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.nio.file.AccessDeniedException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;
    private final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.KiNUK70RDCTWeRMqfN6YY_SAkkb8opFsAh_fwAntt4";

    @BeforeEach
    void setUp() throws AccessDeniedException {
        User user = User.builder()
                .id(1L)
                .email("test123@email.com")
                .password("1234567890")
                .role("string")
                .gitNickname("123124")
                .nickname("test")
                .name("123124")
                .phoneNum("01012341234")
                .build();
        
        given(userService.getUser(1L)).willReturn(user);
        
        given(userService.registerUser(any(UserRegistrationData.class)))
                .will(invocation -> {
                    UserRegistrationData registrationData = invocation.getArgument(0);
                    return User.builder()
                            .id(13L)
                            .email(registrationData.getEmail())
                            .name(registrationData.getName())
                            .password(registrationData.getPassword())
                            .phoneNum(registrationData.getPhoneNum())
                            .blogAddr(registrationData.getBlogAddr())
                            .nickname(registrationData.getNickname())
                            .role(registrationData.getRole())
                            .build();
                });


        given(userService.updateUser(eq(1L), any(UserModificationData.class), eq(1L)))
                .will(invocation -> {
                    Long id = invocation.getArgument(0);
                    UserModificationData modificationData =
                            invocation.getArgument(1);
                    return User.builder()
                            .id(id)
                            .email("tester@example.com")
                            .name("TEST")
                            .nickname(modificationData.getNickname())
                            .phoneNum("01012341234")
                            .password(modificationData.getPassword())
                            .role(modificationData.getRole())
                            .build();
                });

        given(userService.updateUser(eq(100L), any(UserModificationData.class), eq(1L)))
                .willThrow(new UserNotFoundException(100L));

        given(userService.deleteUser(100L))
                .willThrow(new UserNotFoundException(100L));

        given(authenticationService.parseToken(VALID_TOKEN)).willReturn(1L);
    }

    @Test
    void 존재하는_특정_사용자를_조회하는_경우() throws Exception {
        mockMvc.perform(
                get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test123@email.com\", \"name\":\"test\"}")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                )
                .andExpect(status().isOk());
    }
    
    @Test
    void 올바른_정보로_가입하려는_경우() throws Exception {
        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\":\"tester@example.com\"," +
                                        "\"name\":\"Tester\",\"password\":\"test12345\",\"role\":\"junior\",\"nickname\":\"test\",\"phoneNum\":\"01012341234\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"id\":13")
                ))
                .andExpect(content().string(
                        containsString("\"email\":\"tester@example.com\"")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Tester\"")
                ));

        verify(userService).registerUser(any(UserRegistrationData.class));
    }

    @Test
    void 올바르지_않은_정보로_가입하려는_경우() throws Exception {
        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void 올바른_정보로_사용자_정보를_수정하려는_경우() throws Exception {
        mockMvc.perform(
                        patch("/users/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"password\":\"test987654321\",\"role\":\"senior\",\"nickname\":\"test\",\"phoneNum\":\"01012341234\"}")
                                .header("Authorization", "Bearer " + VALID_TOKEN)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"TEST\"")
                ));

        verify(userService).updateUser(eq(1L), any(UserModificationData.class), eq(1L));
    }

    @Test
    void 올바르지_않은_정보로_사용자_정보를_수정하려는_경우() throws Exception {
        mockMvc.perform(
                        patch("/users/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"nickname\":\"\",\"password\":\"\"}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void 존재하지_않는_사용자의_정보를_수정하려는_경우() throws Exception {
        mockMvc.perform(
                        patch("/users/100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"password\":\"test987654321\",\"role\":\"senior\",\"nickname\":\"test\",\"phoneNum\":\"01012341234\"}")
                                .header("Authorization", "Bearer " + VALID_TOKEN)
                )
                .andExpect(status().isNotFound());

        verify(userService)
                .updateUser(eq(100L), any(UserModificationData.class), eq(1L));
    }

    @Test
    void 존재하는_사용자를_삭제하려는_경우() throws Exception {
        mockMvc.perform(delete("/users/1")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                )
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(1L);
    }

    @Test
    void 존재하지_않는_사용자를_삭제하려는_경우() throws Exception {
        mockMvc.perform(delete("/users/100")
                        .header("Authorization", "Bearer " + VALID_TOKEN))
                .andExpect(status().isNotFound());

        verify(userService).deleteUser(100L);
    }
}

