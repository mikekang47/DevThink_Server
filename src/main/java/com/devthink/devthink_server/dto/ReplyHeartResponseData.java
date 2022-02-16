package com.devthink.devthink_server.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class ReplyHeartResponseData {
    private Long id;

    @NotBlank
    private Long userId;

    @NotBlank
    private Long replyId;
}
