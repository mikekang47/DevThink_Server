package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ApiModel("리뷰 상세 정보")
public class ReviewDetailResponseData {

    private UserProfileData userProfile;

    private ReviewResponseData review;

    @Builder.Default
    private List<CommentResponseDto> comments = new ArrayList<>();

}
