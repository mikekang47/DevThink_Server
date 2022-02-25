package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Letter;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.UserRoom;
import com.devthink.devthink_server.dto.LetterSendData;
import com.devthink.devthink_server.infra.LetterRepository;
import com.devthink.devthink_server.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
