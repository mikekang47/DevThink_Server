package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostListData {

    @ApiModelProperty(notes = "게시글 번호", example = "1")
    private Long id;

    @ApiModelProperty(value = "사용자 식별자", example = "1")
    private Long userId;

    @ApiModelProperty(value = "사용자 닉네임", example = "싱크싱크")
    private String nickname;

    @ApiModelProperty(notes = "이미지", example = "testst.com")
    private String imageUrl;

    @ApiModelProperty(notes = "제목", example = "test")
    private String title;

    @ApiModelProperty(notes = "좋아요 수", example = "1")
    private Integer heartCnt;

    @ApiModelProperty(notes = "생성 시각", example = "2022-01-26T22:07:17.0831141")
    private LocalDateTime createAt;

    @ApiModelProperty(notes = "업데이트 시각", example = "2022-01-26T22:07:17.0831141")
    private LocalDateTime updateAt;
}
