package com.devthink.devthink_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserStackResponseData {
    private Long id;

    private Long stackId;

    private Long userId;

}
