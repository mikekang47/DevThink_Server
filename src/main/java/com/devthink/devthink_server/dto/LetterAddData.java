package com.devthink.devthink_server.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@AllArgsConstructor
public class LetterAddData {

    @Mapping("roomId")
    private Long roomId;

    @Mapping("senderId")
    private Long senderId;  // 발신자

    @Mapping("targetId")
    private Long targetId; // 수신자

    @NotBlank(message = "쪽지의 내용을 입력해주세요.")
    @Mapping("content")
    private String content;

    @Mapping("readCheck")
    private Integer readCheck;

    public LetterAddData(){
        this.readCheck = 0;
    }
}
