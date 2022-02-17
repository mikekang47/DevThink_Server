package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Letter;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.UserRoom;
import com.devthink.devthink_server.dto.LetterResultData;
import com.devthink.devthink_server.dto.LetterSendData;
import com.devthink.devthink_server.dto.LetterListData;
import com.devthink.devthink_server.infra.LetterRepository;
import com.devthink.devthink_server.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final UserRepository userRepository;

    /**
     * 메시지 정보를 받아 메시지를 등록합니다.
     * @param userRoom 방 번호
     * @param sender    메시지 전송한 유저 정보
     * @param target    메시지를 전달받을 유저 정보
     * @param data  메시지 정보
     * @return Letter 생성된 메시지
     */
    public Letter createMessage(UserRoom userRoom, User sender, User target, LetterSendData data) {
        Letter letter = letterRepository.save(
                Letter.builder()
                        .content(data.getContent())
                        .heart(data.getHeart())
                        .sender(sender)
                        .target(target)
                        .room(userRoom)
                        .build()
        );
        return letter;
    }

    /**
     * 전달받은 유저 아이디의 방 메시지 리스트를 불러옵니다.
     * @param user 유저 정보
     * @param pageable 페이징 정보
     * @return List<LetterListData> 메시지 리스트
     */
    public List<LetterListData> getMessageList(User user, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "id"));

        List<Letter> messageList = letterRepository.getMessageList(user.getId(), pageable).getContent();

        List<LetterListData> letterListData = messageList.stream()
                .map(Letter::toLetterListData)
                .collect(Collectors.toList());

        setMessageList(user, letterListData);
        return letterListData;
    }

    /**
     * 전달 받은 유저의 방 메시지를 읽음 처리 합니다.
     * @param user 유저 정보
     * @param userRoom  유저의 방 번호
     * @return List<LetterResultData> 채팅방의 메시지 정보
     */
    public List<LetterResultData> getMessage(User user, UserRoom userRoom) {
        List<Letter> unReadLists = letterRepository.getUnReadLists(user.getId(), userRoom.getRoomId());
        for (Letter unreadList : unReadLists) {
            unreadList.setReadCheck(true);
        }

        List<Letter> roomLists = letterRepository.getRoomLists(user.getId(), userRoom.getRoomId());
        return roomLists.stream()
                .map(Letter::toLetterResultData)
                .collect(Collectors.toList());
    }

    /**
     * 상대방의 프로필과 닉네임을 설정합니다.
     * @param user  전달받은 유저 정보
     * @param letterListData  상대방 프로필, 닉네임을 설정할 메시지
     */
    private void setMessageList(User user, List<LetterListData> letterListData) {
        for (LetterListData letter : letterListData) {
            Long unread = letterRepository.countUnread(user.getId(), letter.getRoomId());
            letter.setUnRead(unread);

            String image, Nickname;
            if (user.getId() == letter.getSenderId()) {
                image = userRepository.findImageUrlById(letter.getTargetId());
                Nickname = userRepository.findNicknameById(letter.getTargetId());
                letter.setOtherId(letter.getTargetId());
            } else {
                image = userRepository.findImageUrlById(letter.getSenderId());
                Nickname = userRepository.findNicknameById(letter.getSenderId());
                letter.setOtherId(letter.getSenderId());
            }
            letter.setProfile(image);
            letter.setOtherNick(Nickname);
        }
    }
}
