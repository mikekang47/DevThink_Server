package com.devthink.devthink_server.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ReviewResponseDto {

    private final Long id;

    private final Long userId;

    private final Integer bookIsbn;

    private final String content;

    private final Float score;

    private final String status;

}
