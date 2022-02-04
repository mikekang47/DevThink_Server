package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@ApiModel(value="책 상세 조회 결과 정보")
public class BookDetailResponseData {

    BookResponseData book;

    @ApiModelProperty(value = "리뷰 리스트")
    List<ReviewResponseData> reviews;

}
