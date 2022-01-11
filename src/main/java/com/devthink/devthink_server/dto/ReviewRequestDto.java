package com.devthink.devthink_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewRequestDto {
    private final Long user_id;
    private final Integer book_id;
    private final String content;
    private final Float score;
}
