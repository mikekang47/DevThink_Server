package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.HeartService;
import com.devthink.devthink_server.domain.*;
import com.devthink.devthink_server.dto.HeartCommentResponseData;
import com.devthink.devthink_server.dto.HeartPostResponseData;
import com.devthink.devthink_server.dto.HeartReviewResponseData;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 좋아요의 HTTP 요청을 처리하는 클래스입니다.
 */
@RestController
@RequestMapping("/hearts")
public class HeartController {

    private final HeartService heartService;

    public HeartController(HeartService heartService) {
        this.heartService = heartService;
    }

    /**
     * 좋아요의 식별자를 받아서, 그에 해당하는 좋아요의 정보를 리턴합니다.
     * @param id 찾고자 하는 좋아요의 식별자
     * @return 식별자와 일치하는 좋아요의 정보
     */
    @ApiOperation(value = "좋아요의 식별자", notes = "좋아요의 식별자를 받아서, 그에 해당하는 좋아요의 정보를 리턴합니다.")
    @GetMapping("/{id}")
    public HeartPostResponseData checkHeart(@PathVariable Long id) {
        Heart heart = heartService.getHeart(id);
        return getPostHeartData(heart);
    }

    /**
     * 좋아요를 생성하려는 게시글의 식별자와 사용자의 식별자로 새로운 좋아요를 생성하여, 그 정보를 리턴합니다.
     * @param postId 좋아요를 생성하려는 게시글의 식별자
     * @param userId 좋아요를 생성하는 사용자의 식별자
     * @return 생성된 좋아요의 정보
     */
    @ApiOperation(
            value= "좋아요를 생성하려는 게시글의 식별자와 좋아요를 생성하려는 사용자의 식별자",
            notes = "좋아요를 생성하려는 게시글의 식별자와 사용자의 식별자로 새로운 좋아요를 생성하여, 그 정보를 리턴합니다."
    )
    @PostMapping("/post/{postId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public HeartPostResponseData createPostHeart(@PathVariable Long postId, @PathVariable Long userId) {
        Heart heart = heartService.createPostHeart(postId, userId);
        return getPostHeartData(heart);
    }

    /**
     * 좋아요를 생성하려는 댓글의 식별자와 사용자의 식별자로 새로운 좋아요를 생성하여, 그 정보를 리턴합니다.
     * @param commentId 좋아요를 생성하려는 댓글의 식별자
     * @param userId 좋아요를 생성하는 사용자의 식별자
     * @return 생성된 좋아요의 정보
     */
    @ApiOperation(
            value = "좋아요를 생성하려는 댓글의 식별자와 좋아요를 생성하는 사용자의 식별자",
            notes = "좋아요를 생성하려는 댓글의 식별자와 사용자의 식별자로 새로운 좋아요를 생성하여, 그 정보를 리턴합니다."
    )
    @PostMapping("/comment/{commentId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public HeartCommentResponseData createCommentHeart(@PathVariable Long commentId, @PathVariable Long userId) {
        Heart heart = heartService.createCommentHeart(commentId, userId);
        return getCommentHeartData(heart);
    }

    /**
     * 좋아요를 생성하려는 리뷰의 식별자와 사용자의 식별자로 새로운 좋아요를 생성하여, 그 정보를 리턴합니다.
     * @param reviewId 좋아요를 생성하려는 게시글의 식별자
     * @param userId 좋아요를 생성하는 사용자의 식별자
     * @return 생성된 좋아요의 정보
     */
    @ApiOperation(
            value="좋아요를 생성하려는 게시글의 식별자와 좋아요를 생성하는 사용자의 식별자",
            notes ="좋아요를 생성하려는 리뷰의 식별자와 사용자의 식별자로 새로운 좋아요를 생성하여, 그 정보를 리턴합니다."
    )
    @PostMapping("/review/{reviewtId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public HeartReviewResponseData createReviewHeart(@PathVariable Long reviewId, @PathVariable Long userId) {
        Heart heart = heartService.createReviewHeart(reviewId, userId);
        return getReviewHeartData(heart);
    }

    /**
     * 삭제하고자 하는 좋아요의 식별자를 받아 삭제합니다.
     * @param heartId 삭제하고자 하는 좋아요의 식별자
     */
    @ApiOperation(
            value= "삭제하고자 하는 좋아요의 식별자",
            notes= "삭제하고자 하는 좋아요의 식별자를 받아 삭제합니다."
    )
    @DeleteMapping("/post/{heartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long heartId) {
        heartService.destroyPostHeart(heartId);
    }

    /**
     * 리뷰와 관련한 좋아요의 정보를 받아서 dto로 변환후 리턴합니다.
     * @param heart 변환할 좋아요의 정보
     * @return dto로 변환된 좋아요 정보
     */
    private HeartReviewResponseData getReviewHeartData(Heart heart) {
        return HeartReviewResponseData.builder()
                .id(heart.getId())
                .userId(heart.getUser().getId())
                .reviewId(heart.getReview().getId())
                .build();
    }

    /**
     * 게시글과 관련한 좋아요의 정보를 받아서 dto로 변환후 리턴합니다.
     * @param heart 변환할 좋아요의 정보
     * @return dto로 변환된 좋아요 정보
     */
    private HeartPostResponseData getPostHeartData(Heart heart) {
        return HeartPostResponseData.builder()
                .id(heart.getId())
                .userId(heart.getUser().getId())
                .postId(heart.getPost().getId())
                .build();
    }

    /**
     * 댓글과 관련한 좋아요의 정보를 받아서 dto로 변환후 리턴합니다.
     * @param heart 변환할 좋아요의 정보
     * @return dto로 변환된 좋아요 정보
     */
    private HeartCommentResponseData getCommentHeartData(Heart heart) {
        return HeartCommentResponseData.builder()
                .id(heart.getId())
                .userId(heart.getUser().getId())
                .commentId(heart.getComment().getId())
                .build();
    }
    
}
