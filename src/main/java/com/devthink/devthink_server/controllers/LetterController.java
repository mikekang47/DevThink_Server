package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.domain.Letter;
import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.dto.LetterAddData;
import com.devthink.devthink_server.dto.LetterResultData;
import com.devthink.devthink_server.dto.PostResultData;
import com.devthink.devthink_server.service.LetterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


// 메시지 전송 코드
@RestController
@RequestMapping("/messages")
public class LetterController {

    private LetterService letterService;

    public LetterController(LetterService letterService) {
        this.letterService = letterService;
    }

    /**
     * 쪽지 보내기 기능
     * @param
     * @return
     */
    @PostMapping
    public ResponseEntity<String> addMessage(@RequestBody @Valid LetterAddData letterAddData){
        ResponseEntity<String> entity = null;
        try {
            letterService.addMessage(letterAddData);
            entity = new ResponseEntity<String>("쪽지를 성공적으로 보냈습니다.", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
        return entity;
    }

    /**
     * 메시지 목록
     */
    @GetMapping
    public List<LetterResultData> messageList(@RequestParam Long user_id)
    {
        ArrayList<Letter> letters = letterService.messageList(user_id);
        return getLetterDtos(letters);
    }


    private List<LetterResultData> getLetterDtos(List<Letter> letters) {
        List<LetterResultData> letterDtos = new ArrayList<>();

        for (Letter letter : letters) {
            letterDtos.add(getLetterData(letter));
        }
        return letterDtos;
    }


    /**
     * 게시글의 정보를 받아 게시글을 dto 데이터로 변환하여 반환합니다.
     * @param letter 게시글 정보
     * @return 입력된 dto 데이터로 변환된 값
     */
    private LetterResultData getLetterData(Letter letter)
    {
        if(letter == null)
            return null;

        return LetterResultData.builder()
                .content(letter.getContent())
                .readCheck(letter.getReadCheck())
                .roomId(letter.getRoomId())
                .senderId(letter.getSenderId())
                .targetId(letter.getTargetId())
                .view_at(letter.getView_at())
                .create_at(letter.getCreate_at())
                .build();

    }

}