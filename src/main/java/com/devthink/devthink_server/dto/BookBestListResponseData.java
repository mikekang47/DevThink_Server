package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@ApiModel(value="일정 기간 동안 리뷰가 가장 많이 달린 책 5개 조회 결과 정보")
public class BookBestListResponseData {

    @ApiModelProperty(value = "리뷰 집계 시작 시간(저번주 시작 시간)")
    LocalDateTime start;

    @ApiModelProperty(value = "리뷰 집계 끝 시간(이번주 시작 시간)")
    LocalDateTime end;

    @ApiModelProperty(value = "정렬된 책 리스트")
    List<BookResponseData> books;

}
