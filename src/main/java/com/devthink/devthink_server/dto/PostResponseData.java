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
public class PostResponseData {

    @ApiModelProperty(notes = "게시글 번호", example = "1")
    private Long id;

    @ApiModelProperty(notes = "유저 아이디", example = "1")
    private Long user_id;

    @ApiModelProperty(notes = "카테고리 아이디", example = "1")
    private Long category_id;

    @ApiModelProperty(notes = "제목", example = "test")
    private String title;

    @ApiModelProperty(notes = "내용", example = "Test")
    private String content;

    @ApiModelProperty(notes = "글 상태", example = "active")
    private String status;

    @ApiModelProperty(notes = "좋아요 수", example = "1")
    private Integer like;

    @ApiModelProperty(notes = "생성 시각", example = "2022-01-26T22:07:17.0831141")
    private LocalDateTime createAt;

    @ApiModelProperty(notes = "업데이트 시각", example = "2022-01-26T22:07:17.0831141")
    private LocalDateTime updateAt;

}
