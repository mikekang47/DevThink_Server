package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Letter;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.UserRoom;
import com.devthink.devthink_server.dto.LetterListData;
import com.devthink.devthink_server.dto.LetterSendData;
import com.devthink.devthink_server.infra.LetterRepository;
import com.devthink.devthink_server.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LetterServiceTest {

    private LetterService letterService;
    private LetterRepository letterRepository = mock(LetterRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setup(){

        letterService = new LetterService(letterRepository, userRepository);

        given(letterRepository.save(any(Letter.class))).will(
                invocation -> {
                    User sender = User.builder().id(1L).build();
                    User target = User.builder().id(2L).build();
                    UserRoom userRoom = UserRoom.builder().roomId(1L).user1(sender).user2(target).build();

                    return Letter.builder()
                            .id(1L)
                            .room(userRoom)
                            .sender(sender)
                            .target(target)
                            .content("test")
                            .build();
                }
        );

        given(letterRepository.getMessageList(any(Long.class))).will(
                invocation -> {
                    User sender = User.builder().id(1L).build();
                    User target = User.builder().id(2L).build();
                    UserRoom userRoom = UserRoom.builder().roomId(1L).user1(sender).user2(target).build();

                    Letter letter = Letter.builder()
                            .id(1L)
                            .room(userRoom)
                            .sender(sender)
                            .target(target)
                            .content("test")
                            .build();

                    List<Letter> letters = new ArrayList<>();
                    letters.add(letter);
                    return letters;
                }
        );

        given(letterRepository.getUnReadLists(any(Long.class), any(Long.class))).will(
                invocation -> {
                    User sender = User.builder().id(1L).build();
                    Long userId = invocation.getArgument(0);
                    Long roomId = invocation.getArgument(1);

                    User target = User.builder().id(userId).build();
                    UserRoom userRoom = UserRoom.builder().roomId(roomId).user1(sender).user2(target).build();

                    Letter letter = Letter.builder()
                            .id(1L)
                            .room(userRoom)
                            .sender(sender)
                            .target(target)
                            .content("test")
                            .readCheck(false)
                            .build();

                    List<Letter> letters = new ArrayList<>();
                    letters.add(letter);

                    return letters;
                }
        );

        given(letterRepository.countUnread(any(Long.class), any(Long.class))).willReturn(1L);

        given(userRepository.findImageUrlById(any(Long.class))).willReturn("test.com");
        given(userRepository.findNicknameById(any(Long.class))).willReturn("tester");
    }

    @Test
    void 올바른_정보로_쪽지를_보내는_경우() {
        User sender = User.builder().id(1L).build();
        User target = User.builder().id(2L).build();
        UserRoom userRoom = UserRoom.builder().roomId(1L).user1(sender).user2(target).build();
        LetterSendData letterSendData = LetterSendData.builder().content("tester").nickname("testst").heart(false).build();

        Letter letter = letterService.createMessage(userRoom, sender, target, letterSendData);

        assertThat(letter.getContent()).isEqualTo("test");
        assertThat(letter.getId()).isEqualTo(1L);

        verify(letterRepository).save(any(Letter.class));
    }

    @Test
    void 올바른_정보로_메시지_리스트를_불러오는_경우() {
        User user = User.builder().id(2L).build();

        List<LetterListData> messageList = letterService.getMessageList(user);

        assertThat(messageList.get(0).getContent()).isEqualTo("test");
        assertThat(messageList.get(0).getOtherNick()).isEqualTo("tester");
        assertThat(messageList.get(0).getProfile()).isEqualTo("test.com");
    }

    @Test
    void 올바른_정보로_메시지를_읽음처리_하는_경우() {
        User sender = User.builder().id(1L).build();
        User target = User.builder().id(2L).build();
        UserRoom userRoom = UserRoom.builder().roomId(1L).user1(sender).user2(target).build();

        List<Letter> letters = letterService.setReadCheck(target, userRoom);

        assertThat(letters.get(0).getSender().getId()).isEqualTo(1L);
        assertThat(letters.get(0).getTarget().getId()).isEqualTo(2L);
        assertThat(letters.get(0).isReadCheck()).isEqualTo(true);
    }

}
