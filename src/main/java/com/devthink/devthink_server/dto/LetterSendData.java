package com.devthink.devthink_server.dto;

import com.github.dozermapper.core.Mapping;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
public class LetterSendData {
    @Mapping("roomId")
    @ApiModelProperty(value = "방 번호", required = true, example = "1")
    @NotNull
    private Long roomId;

    @Mapping("nickname")
    @ApiModelProperty(value = "상대방 닉네임", required = true, example = "tester22")
    @NotBlank(message = "메시지를 받을 사람의 닉네임을 입력해주세요.")
    private String nickname;

    @Mapping("content")
    @ApiModelProperty(value = "메시지 내용", required = true, example = "test")
    @NotBlank(message = "메시지 내용을 입력해주세요.")
    private String content;

    @Mapping("heart")
    @ApiModelProperty(value = "하트 이모티콘 포함 여부", example = "true")
    private Boolean heart;

    public LetterSendData() {
        heart = false;
    }

    public void changeRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
