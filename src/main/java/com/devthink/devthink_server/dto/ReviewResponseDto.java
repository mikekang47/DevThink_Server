package com.devthink.devthink_server.dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
public class ReviewResponseDto {

    private Long id;

    private Long userId;

    private Integer bookIsbn;

    private String content;

    private BigDecimal score;

    @Builder
    public ReviewResponseDto(Long id, Long userId, Integer bookIsbn, String content, BigDecimal score){
        this.id = id;
        this.userId = userId;
        this.bookIsbn = bookIsbn;
        this.content = content;
        this.score = score;
    }

}
