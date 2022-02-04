package com.devthink.devthink_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HeartReviewResponseData {
    Long id;

    Long userId;

    Long reviewId;
}
