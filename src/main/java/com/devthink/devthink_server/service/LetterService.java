package com.devthink.devthink_server.service;

import com.devthink.devthink_server.domain.Letter;
import com.devthink.devthink_server.dto.LetterAddData;
import com.devthink.devthink_server.dto.LetterResultData;
import com.devthink.devthink_server.infra.LetterRepository;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Transactional
@Service
public class LetterService {

    private final LetterRepository letterRepository;
    private final Mapper mapper;

    public LetterService(LetterRepository letterRepository, Mapper mapper) {
        this.letterRepository = letterRepository;
        this.mapper = mapper;
    }

    /**
     * 사용자로부터 메시지 정보를 받아 데이터베이스에 저장합니다.
     * @param dto 사용자 메시지 입력 데이터
     */
    public void addMessage(LetterAddData dto){
        Letter letter = mapper.map(dto, Letter.class);
        letterRepository.save(letter);

    }

    /**
     * 전달받은 user_id(유저 아이디)를 통해 방 별 메시지 리스트를 불러옵니다.
     * @param user_id 보여주고자 하는 유저 고유 아이디값
     * @return ArrayList<Letter> 메시지 리스트 값 반환
     */
    public ArrayList<Letter> messageList(Long user_id) {
        // 메시지 리스트에 나타낼 것들: 가장 최근 메시지, 보낸 사람 id
        ArrayList<Letter> list = letterRepository.getMessageList(user_id);

        for (Letter letter : list) {
            // 현재 사용자가 안읽은 메시지의 개수를 가져옵니다.
            Long unread = letterRepository.countUnread(user_id, letter.getRoomId());

            // 읽지 않은 메시지 갯수를 letter에 change 합니다.
            letter.change(unread);

            // 메시지 상대 id를 세팅합니다.
            if(user_id == letter.getSenderId()) {
                letter.changeOtherId(letter.getTargetId());
            }
            else{
                letter.changeOtherId(letter.getSenderId());
            }
        }
        return list;
    }

    /**
     * 전달 받은 유저의 방 별 메시지 내용을 가져오고, 조회수를 업데이트 합니다.
     * @param user_id 조회하고자 하는 유저 id
     *        room_id 조회하고자 하는 유저의 방 id
     * @return ArrayList<Letter> 메시지 리스트 값 반환
     */
    public ArrayList<Letter> roomContentList(Long user_id, Long room_id)
    {
        ArrayList<Letter> roomLists = letterRepository.getRoomLists(user_id, room_id);
        letterRepository.MessageReadCheck(user_id, room_id);
        return roomLists;
    }
}