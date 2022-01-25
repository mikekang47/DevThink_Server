package com.devthink.devthink_server.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class BookResponseDto {

    private Long id;

    private Integer isbn;

    private Integer reviewCnt;

    private BigDecimal scoreAvg;

}
