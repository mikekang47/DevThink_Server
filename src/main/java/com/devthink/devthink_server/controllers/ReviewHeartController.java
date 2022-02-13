package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.ReviewHeartService;
import com.devthink.devthink_server.domain.Heart;
import com.devthink.devthink_server.domain.ReviewHeart;
import com.devthink.devthink_server.dto.ReviewHeartResponseData;
import com.devthink.devthink_server.security.UserAuthentication;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reviews/hearts")
public class ReviewHeartController {
    private final ReviewHeartService reviewHeartService;

    public ReviewHeartController(ReviewHeartService reviewHeartService) {
        this.reviewHeartService = reviewHeartService;
    }

    /**
     * 좋아요를 생성하려는 리뷰 식별자와 사용자 식별자로 새로운 리뷰 좋아요를 생성하여, 그 정보를 리턴합니다.
     * @param reviewId 좋아요를 생성하려는 리뷰 식별자
     * @return 생성된 좋아요의 정보
     */
    @ApiOperation(
            value = "리뷰 좋아요 생성",
            notes ="좋아요를 생성하려는 리뷰의 식별자와 사용자 토큰으로 새로운 리뷰 좋아요를 생성하여, 생성된 좋아요의 정보를 리턴합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = ReviewHeartResponseData.class
    )
    @PostMapping("/reviews/{reviewId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewHeartResponseData createReviewHeart(@PathVariable Long reviewId, UserAuthentication userAuthentication) {
        Long userId = userAuthentication.getUserId();
        ReviewHeart reviewHeart = reviewHeartService.createReviewHeart(reviewId, userId);
        return getReviewHeartData(reviewHeart);
    }

    /**
     * 좋아요를 취소하고자 하는 리뷰의 식별자를 받아 좋아요를 취소합니다.
     * @param reviewId 좋아요를 취소하고자 하는 리뷰의 식별자
     */
    @ApiOperation(
            value= "리뷰 좋아요 삭제(좋아요 취소)",
            notes= "좋아요를 취소하고자 하는 댓글의 식별자를 받아 좋아요를 취소합니다. 헤더에 사용자 토큰 주입을 필요로 합니다."
    )
    @ApiImplicitParam(name="reviewId", dataType = "integer", value = "좋아요를 취소하고자 하는 리뷰의 식별자")
    @DeleteMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void destroyReviewHeart(@PathVariable Long reviewId) {
        reviewHeartService.destroyReviewHeart(reviewId);
    }

    /**
     * 리뷰와 관련한 좋아요의 정보를 받아서 dto로 변환후 리턴합니다.
     * @param reviewHeart 변환할 좋아요의 정보
     * @return dto로 변환된 좋아요 정보
     */
    private ReviewHeartResponseData getReviewHeartData(ReviewHeart reviewHeart) {
        return ReviewHeartResponseData.builder()
                .id(reviewHeart.getId())
                .userId(reviewHeart.getUser().getId())
                .reviewId(reviewHeart.getReview().getId())
                .build();
    }

}
