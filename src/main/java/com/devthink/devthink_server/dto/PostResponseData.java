package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseData {

    @ApiModelProperty(notes = "게시글 번호", example = "1")
    private Long id;

    private UserProfileData userProfile;

    @ApiModelProperty(notes = "카테고리 아이디", example = "1")
    private Long categoryId;

    @ApiModelProperty(notes = "이미지", example = "testst.com")
    private String imageUrl;

    @ApiModelProperty(notes = "이미지 포함여부", example = "false")
    private boolean Image;

    @ApiModelProperty(notes = "제목", example = "test")
    private String title;

    @ApiModelProperty(notes = "프로젝트 부제목", example = "test")
    private String subTitle;

    @ApiModelProperty(notes = "내용", example = "Test")
    private String content;

    @ApiModelProperty(notes = "글 상태", example = "active")
    private Boolean deleted;

    @ApiModelProperty(notes = "좋아요 수", example = "1")
    private Integer heartCnt;

    @ApiModelProperty(notes = "게시글 댓글")
    @Builder.Default
    private List<CommentDetailResponseData> comments = new ArrayList<>();

    @ApiModelProperty(notes = "생성 시각", example = "2022-01-26T22:07:17.0831141")
    private LocalDateTime createAt;

    @ApiModelProperty(notes = "업데이트 시각", example = "2022-01-26T22:07:17.0831141")
    private LocalDateTime updateAt;

}
