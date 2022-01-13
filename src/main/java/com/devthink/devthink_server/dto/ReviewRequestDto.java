package com.devthink.devthink_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewRequestDto {

    private final Long userId;

    private final Integer bookIsbn;

    private final String content;

    private final Float score;

}
