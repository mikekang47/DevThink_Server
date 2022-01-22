package com.devthink.devthink_server.service;

import com.devthink.devthink_server.domain.Letter;
import com.devthink.devthink_server.dto.LetterAddData;
import com.devthink.devthink_server.dto.LetterResultData;
import com.devthink.devthink_server.infra.LetterRepository;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
     * 메시지 작성(DB 저장)
     * @param dto
     */
    public void addMessage(LetterAddData dto){
        Letter letter = mapper.map(dto, Letter.class);
        letterRepository.save(letter);

    }

    /**
     * user_id의 메시지 리스트 가져오기
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
            if(user_id == letter.getSenderId())
            {
                letter.changeOtherId(letter.getTargetId());
            }
            else{
                letter.changeOtherId(letter.getSenderId());
            }

        }
        return list;
    }

    /**
     * room 별 메시지 내용을 가져옵니다.
     */



}