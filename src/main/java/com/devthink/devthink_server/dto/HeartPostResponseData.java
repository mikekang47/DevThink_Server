package com.devthink.devthink_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class HeartPostResponseData {
    private Long id;

    private Long userId;

    private Long postId;
}
