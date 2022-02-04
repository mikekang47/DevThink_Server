package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
@ApiModel(value = "게시글 좋아요 생성 정보")
public class HeartPostResponseData {
    private Long id;

    @ApiParam(value = "사용자 식별자", required = true, example = "10")
    private Long userId;

    @ApiParam(value = "게시글 식별자", required = true, example = "25")
    private Long postId;
}
