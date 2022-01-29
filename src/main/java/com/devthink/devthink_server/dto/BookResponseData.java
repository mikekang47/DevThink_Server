package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@ApiModel(value="책 조회 결과 정보")
public class BookResponseData {

    @ApiModelProperty(value = "책 식별자", example = "1")
    private Long id;

    @ApiModelProperty(value = "책 isbn 번호", example = "8960773417")
    private Integer isbn;

    @ApiModelProperty(value = "책 이름", example = "토비의 스프링 3.1 Vol. 1: 스프링의 이해와 원리(에이콘 오픈소스 프로그래밍 시리즈 18)")
    private String name;

    @ApiModelProperty(value = "책 저자", example = "이일민")
    private String writer;

    @ApiModelProperty(value = "책 표지 이미지 url", example = "https://image.aladin.co.kr/product/19700/99/cover500/e896077341_2.jpg")
    private String imgUrl;

    @ApiModelProperty(value = "리뷰 수", example = "3")
    private Integer reviewCnt;

    @ApiModelProperty(value = "책 평점", example = "3.6")
    private BigDecimal scoreAvg;

}
