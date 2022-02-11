package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel(value="책 등록 요청 정보")
public class BookRequestData {

    @NotNull
    @ApiModelProperty(value = "책 isbn 번호", required = true, example = "8960773417")
    private String isbn;

    @NotBlank
    @ApiModelProperty(value = "책 이름", required = true, example = "토비의 스프링 3.1 Vol. 1: 스프링의 이해와 원리(에이콘 오픈소스 프로그래밍 시리즈 18)")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "책 저자", required = true, example = "이일민")
    private String writer;

    @ApiModelProperty(value = "책 표지 이미지 url", required = true, example = "https://image.aladin.co.kr/product/19700/99/cover500/e896077341_2.jpg")
    private String imgUrl;

}
