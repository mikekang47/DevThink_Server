package com.devthink.devthink_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentRequestDto {
    private final Long user_id;
    private final Long review_id;
    private final String content;
}
