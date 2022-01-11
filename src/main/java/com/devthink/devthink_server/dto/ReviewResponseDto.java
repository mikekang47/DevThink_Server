package com.devthink.devthink_server.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ReviewResponseDto {
    private final Long id;
    private final String user_id;
    private final String book_id;
    private final String content;
    private final Float score;
}
