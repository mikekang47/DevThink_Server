package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LetterListData {

    @ApiModelProperty(notes = "방 번호", example = "1")
    private Long roomId;

    @ApiModelProperty(notes = "보낸 사람", example = "1")
    private Long senderId;

    @ApiModelProperty(notes = "받는 사람", example = "1")
    private Long targetId;

    @ApiModelProperty(notes = "메시지 내용", example = "test")
    private String content;

    @ApiModelProperty(notes = "새 메시지 갯수", example = "1")
    private Long unRead;

    @ApiModelProperty(notes = "상대방 인덱스", example = "test")
    private Long otherId;

    @ApiModelProperty(notes = "상대방 닉네임", example = "test")
    private String otherNick;

    @ApiModelProperty(notes = "상대방 프로필 이미지", example = "example.com")
    private String profile;

    @ApiModelProperty(notes = "보낸 시각", example = "2022-01-26T22:07:17.083114")
    private LocalDateTime createAt;

}
