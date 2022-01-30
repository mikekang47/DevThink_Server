package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@ApiModel("댓글 등록 정보")
public class CommentRequestDto {

    @ApiParam(value = "회원 식별자", required = true, example = "1")
    @NotBlank
    private final Long userId;

    @ApiParam(value = "게시글 식별자", example = "1")
    private final Long postId;

    @ApiParam(value = "리뷰 식별자", example = "1")
    private final Long reviewId;

    @ApiParam(value = "댓글 내용", required = true, example = "안녕하세요")
    @NotBlank
    @Size(min=1)
    private final String content;

}
