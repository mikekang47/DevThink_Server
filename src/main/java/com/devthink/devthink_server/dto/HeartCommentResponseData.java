package com.devthink.devthink_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class HeartCommentResponseData {
    private Long id;

    private Long userId;

    private Long commentId;
}
