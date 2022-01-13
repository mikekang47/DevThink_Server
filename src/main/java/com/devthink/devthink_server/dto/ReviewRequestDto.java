package com.devthink.devthink_server.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRequestDto {

    private Long userId;

    private Integer bookIsbn;

    private String content;

    private Float score;

}
