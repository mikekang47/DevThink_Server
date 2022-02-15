package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@ApiModel(value = "댓글 좋아요 생성 정보")
public class CommentHeartResponseData {
    private Long id;

    @NotBlank
    @ApiParam(value = "사용자 식별자", required = true, example = "24")
    private Long userId;

    @NotBlank
    @ApiParam(value = "댓글 식별자", required = true, example = "24")
    private Long commentId;
}
