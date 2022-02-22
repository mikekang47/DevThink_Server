package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.AuthenticationService;
import com.devthink.devthink_server.application.LetterService;
import com.devthink.devthink_server.application.UserRoomService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.Letter;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.UserRoom;
import com.devthink.devthink_server.dto.LetterListData;
import com.devthink.devthink_server.dto.LetterSendData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LetterController.class)
public class LetterControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LetterService letterService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRoomService userRoomService;

    @MockBean
    private AuthenticationService authenticationService;
    private final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.KiNUK70RDCTWeRMqfN6YY_SAkkb8opFsAh_fwAntt4";

    @BeforeEach
    void setup() throws AccessDeniedException {
        User sender = User.builder().id(1L).build();
        User target = User.builder().id(2L).nickname("test").build();

        given(userService.getUser(1L)).willReturn(sender);

        given(letterService.findByNickname(eq("test"))).willReturn(target);

        given(userRoomService.existChat(eq(1L), eq(2L))).willReturn(0);

        given(userRoomService.getMaxRoom()).willReturn(null);

        given(userRoomService.save(any(User.class), any(User.class), eq(1L))).will(
                invocation -> {
                    User senderUser = invocation.getArgument(0);
                    User targetUser = invocation.getArgument(1);

                    return UserRoom.builder()
                            .user1(senderUser)
                            .user2(targetUser)
                            .roomId(1L)
                            .build();

                });

        given(letterService.createMessage(any(UserRoom.class), any(User.class), any(User.class), any(LetterSendData.class)))
                .will(
                invocation -> {
                    UserRoom userRoom = invocation.getArgument(0);
                    User senderUser = invocation.getArgument(1);
                    User targetUser = invocation.getArgument(2);
                    LetterSendData letterSendData = invocation.getArgument(3);

                    return Letter.builder()
                            .target(targetUser)
                            .sender(senderUser)
                            .room(userRoom)
                            .content(letterSendData.getContent())
                            .build();

                });

        given(letterService.getMessageList(any(User.class))).will(
                invocation -> {
                    User user = invocation.getArgument(0);
                    UserRoom userRoom = UserRoom.builder().roomId(1L).user1(user).user2(target).build();

                    Letter letter = Letter.builder()
                            .sender(user)
                            .target(target)
                            .room(userRoom)
                            .content("test")
                            .build();

                    List<Letter> letters = new ArrayList<>();
                    letters.add(letter);

                    List<LetterListData> letterList = letters.stream()
                            .map(Letter::toLetterListData)
                            .collect(Collectors.toList());

                    letterList.get(0).setOtherId(2L);
                    letterList.get(0).setOtherNick("test");

                    return letterList;
                }
        );

        given(authenticationService.parseToken(VALID_TOKEN)).willReturn(1L);

    }
    @Test
    void 올바른_정보로_사용자_프로필에서_쪽지를_보내는_경우() throws Exception {
        mvc.perform(
                post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roomId\": 0, \"nickname\":\"test\", \"content\":\"test\"}")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
        )
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"roomId\":1")
                ))
                .andExpect(content().string(
                        containsString("\"senderId\":1")
                ))
                .andExpect(content().string(
                        containsString("\"targetId\":2")
                ))
                .andExpect(content().string(
                        containsString("\"targetNick\":\"test\"")
                ))
                .andExpect(content().string(
                        containsString("\"content\":\"test\"")
                ));
        verify(letterService).createMessage(any(UserRoom.class), any(User.class), any(User.class), any(LetterSendData.class));

    }

    @Test
    void 올바른_정보로_쪽지_리스트를_불러오는_경우() throws Exception {
        mvc.perform(
                get("/messages/lists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + VALID_TOKEN)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"roomId\":1")
                ))
                .andExpect(content().string(
                        containsString("\"senderId\":1")
                ))
                .andExpect(content().string(
                        containsString("\"targetId\":2")
                ))
                .andExpect(content().string(
                        containsString("\"otherNick\":\"test\"")
                ))
                .andExpect(content().string(
                        containsString("\"content\":\"test\"")
                ));
    }
}

