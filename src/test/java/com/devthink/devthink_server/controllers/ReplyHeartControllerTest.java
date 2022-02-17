package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.*;
import com.devthink.devthink_server.domain.Comment;
import com.devthink.devthink_server.domain.Reply;
import com.devthink.devthink_server.domain.ReplyHeart;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.UserRegistrationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReplyHeartController.class)
class ReplyHeartControllerTest {
    private final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjF9.KiNUK70RDCTWeRMqfN6YY_SAkkb8opFsAh_fwAntt4";

    @Autowired
    MockMvc mvc;

    @MockBean
    ReplyHeartService replyHeartService;

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    UserService userService;

    @MockBean
    ReplyService replyService;

    @MockBean
    CommentService commentService;

    @BeforeEach
    void setUp() {
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

        given(replyService.createReply(any(User.class), any(Comment.class), eq("123123123"))).will(
                invocation -> {
                    User user = invocation.getArgument(0);
                    Comment comment = invocation.getArgument(1);
                    String content = invocation.getArgument(2);
                    return Reply.builder()
                            .user(user)
                            .id(12L)
                            .content(content)
                            .comment(comment)
                            .build();
                }
        );

        given(replyHeartService.create(eq(13L), eq(12L))).will(invocation -> {
            Reply reply = replyService.getReply(invocation.getArgument(0));
            User user = userService.getUser(invocation.getArgument(1));
            return ReplyHeart.builder()
                    .id(1L)
                    .user(user)
                    .reply(reply)
                    .build();
        });


        
    }

    @Test
    void createReplyHeartWithValidReplyId() throws Exception {
        mvc.perform(post("/replies/hearts/1")
                        .header("Authorization","Bearer " + VALID_TOKEN)

                )
                .andExpect(status().isCreated());

    }

}
