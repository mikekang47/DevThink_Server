package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@ApiModel(value = "답글 좋아요 생성 정보")
public class ReplyHeartResponseData {
    private Long id;

    @NotBlank
    @ApiParam(name="userId", value="사용자 식별자", required = true, example="12")
    private Long userId;

    @NotBlank
    @ApiParam(name="replyId", value="답글 식별자", required = true, example="12")
    private Long replyId;
}
