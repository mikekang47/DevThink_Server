package com.devthink.devthink_server.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@AllArgsConstructor
public class LetterSendData {
    @Mapping("roomId")
    private Long roomId;

    @Mapping("senderId")
    private Long senderId;

    @Mapping("targetId")
    private Long targetId;

    @Mapping("content")
    private String content;

    @Mapping("heart")
    private Boolean heart;

    public LetterSendData() {
        heart = false;
    }

    public void changeRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
