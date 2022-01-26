package com.devthink.devthink_server.dto;

import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRequestDto {

    private Long userId;

    private BookRequestDto book;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @DecimalMin("0") @DecimalMax("5")
    private BigDecimal score;

    public ReviewRequestDto(String content){
        this.content = content;
    }

    public ReviewRequestDto(BigDecimal score){
        this.score = score;
    }
}
