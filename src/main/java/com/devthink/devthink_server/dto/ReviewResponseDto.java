package com.devthink.devthink_server.dto;


import lombok.*;

@Getter
@Setter
public class ReviewResponseDto {

    private Long id;

    private Long userId;

    private Integer bookIsbn;

    private String content;

    private Float score;

    @Builder
    public ReviewResponseDto(Long id, Long userId,Integer bookIsbn,String content,Float score){
        this.id = id;
        this.userId = userId;
        this.bookIsbn = bookIsbn;
        this.content = content;
        this.score = score;
    }

}
