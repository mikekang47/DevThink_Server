package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LetterResultData {
    @ApiModelProperty(notes = "방 번호", example = "1")
    private Long roomId;

    @ApiModelProperty(notes = "메시지 발신자", example = "1")
    private Long senderId;

    @ApiModelProperty(notes = "메시지 수신자", example = "2")
    private Long targetId;

    @ApiModelProperty(notes = "발신자 닉네임", example = "케빈")
    private String sendNick;

    @ApiModelProperty(notes = "수신자 닉네임", example = "룰루")
    private String targetNick;

    @ApiModelProperty(notes = "메시지 내용", example = "example")
    private String content;

    @ApiModelProperty(notes = "메시지 읽음 체크", example = "true")
    private Boolean readCheck;

    @ApiModelProperty(notes = "하트 송신 여부", example = "true")
    private Boolean heart;

    @ApiModelProperty(notes = "메시지 보낸 시각", example = "2022-01-26T22:07:17.0831141")
    private LocalDateTime createAt;
}
