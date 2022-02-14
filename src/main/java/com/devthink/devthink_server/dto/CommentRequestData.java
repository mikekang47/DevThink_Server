package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@ApiModel("댓글 등록 정보")
public class CommentRequestData {

    @ApiModelProperty(value = "회원 식별자", required = true, example = "1")
    @NotNull
    private final Long userId;

    @ApiModelProperty(value = "게시글 식별자", example = "1")
    private final Long postId;

    @ApiModelProperty(value = "리뷰 식별자", example = "1")
    private final Long reviewId;

    @ApiModelProperty(value = "댓글 내용", required = true, example = "안녕하세요")
    @NotBlank(message = "Comment content cannot be empty")
    @Size(min=1)
    private final String content;

}
