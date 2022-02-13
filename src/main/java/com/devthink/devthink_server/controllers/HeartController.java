package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.HeartService;
import com.devthink.devthink_server.domain.*;
import com.devthink.devthink_server.dto.HeartCommentResponseData;
import com.devthink.devthink_server.dto.HeartPostResponseData;
import com.devthink.devthink_server.dto.HeartReviewResponseData;
import com.devthink.devthink_server.security.UserAuthentication;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @ApiOperation(value = "좋아요 조회", notes = "좋아요의 식별자를 받아서, 그에 해당하는 좋아요의 정보를 리턴합니다.")
    @ApiImplicitParam(name ="id", dataType = "integer", value = "좋아요 식별자")
    @GetMapping("/{id}")
    public HeartPostResponseData checkHeart(@PathVariable Long id) {
        Heart heart = heartService.getHeart(id);
        return getPostHeartData(heart);
    }

    /**
     * 좋아요를 생성하려는 게시글 객체와 사용자 토큰으로 새로운 좋아요를 생성하여, 그 정보를 리턴합니다.
     * @param postId 좋아요를 생성하려는 게시글의 식별자
     * @header accessToken 사용자의 토큰
     * @return 생성된 좋아요의 정보
     */
    @ApiOperation(
            value= "개시글 좋아요 생성",
            notes = "좋아요를 생성하려는 게시글 객체와 사용자 토큰으로 새로운 게시글 좋아요를 생성하여, 그 정보를 리턴합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = HeartPostResponseData.class
    )
    @PostMapping("/post/{postId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public HeartPostResponseData createPostHeart(@PathVariable Long postId, UserAuthentication userAuthentication) {
        Long userId = userAuthentication.getUserId();
        Heart heart = heartService.createPostHeart(postId, userId);
        return getPostHeartData(heart);
    }

    /**
     * 좋아요를 생성하려는 댓글의 식별자와 사용자 토큰으로 새로운 댓글 좋아요를 생성하여, 그 정보를 리턴합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.
     * @param commentId 좋아요를 생성하려는 댓글의 식별자
     * @return 생성된 좋아요의 정보
     */
    @ApiOperation(
            value = "댓글 좋아요 생성",
            notes = "좋아요를 생성하려는 댓글의 객체와 사용자 객체로 새로운 좋아요를 생성하여, 생성된 좋아요의 정보를 리턴합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = HeartCommentResponseData.class
    )
    @PostMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public HeartCommentResponseData createCommentHeart(@PathVariable Long commentId, UserAuthentication userAuthentication) {
        Long userId = userAuthentication.getUserId();
        Heart heart = heartService.createCommentHeart(commentId, userId);
        return getCommentHeartData(heart);
    }

    /**
     * 좋아요를 생성하려는 리뷰 객체와 사용자 객체로 새로운 좋아요를 생성하여, 그 정보를 리턴합니다.
     * @param reviewId 좋아요를 생성하려는 리뷰 식별자
     * @return 생성된 좋아요의 정보
     */
    @ApiOperation(
            value = "리뷰 좋아요 생성",
            notes ="좋아요를 생성하려는 리뷰 객체와 사용자 객체로 새로운 좋아요를 생성하여, 생성된 좋아요의 정보를 리턴합니다.",
            response = HeartReviewResponseData.class
    )
    @PostMapping("/review/{reviewId}")
    @ResponseStatus(HttpStatus.CREATED)
    public HeartReviewResponseData createReviewHeart(@PathVariable Long reviewId, UserAuthentication userAuthentication) {
        Long userId = userAuthentication.getUserId();
        Heart heart = heartService.createReviewHeart(reviewId, userId);
        return getReviewHeartData(heart);
    }

    /**
     * 삭제하고자 하는 좋아요의 식별자를 받아 삭제합니다.
     * @param heartId 삭제하고자 하는 좋아요의 식별자
     */
    @ApiOperation(
            value= "좋아요 삭제(좋아요 취소)",
            notes= "삭제하고자 하는 좋아요의 식별자를 받아 삭제합니다."
    )
    @ApiImplicitParam(name="heartId", dataType = "integer", value = "좋아요 식별자")
    @DeleteMapping("/{heartId}/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyPostHeart(@PathVariable Long heartId, @PathVariable Long postId) {
        heartService.destroyPostHeart(heartId, postId);
    }

    /**
     * 삭제하고자 하는 좋아요의 식별자를 받아 삭제합니다.
     * @param heartId 삭제하고자 하는 좋아요의 식별자
     */
    @ApiOperation(
            value= "좋아요 삭제(좋아요 취소)",
            notes= "삭제하고자 하는 좋아요의 식별자를 받아 삭제합니다."
    )
    @ApiImplicitParam(name="heartId", dataType = "integer", value = "좋아요 식별자")
    @DeleteMapping("/{heartId}/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyReviewHeart(@PathVariable Long heartId, @PathVariable Long reviewId) {
        heartService.destroyReviewHeart(heartId, reviewId);
    }

    /**
     * 삭제하고자 하는 좋아요의 식별자를 받아 삭제합니다.
     * @param heartId 삭제하고자 하는 좋아요의 식별자
     */
    @ApiOperation(
            value= "좋아요 삭제(좋아요 취소)",
            notes= "삭제하고자 하는 좋아요의 식별자를 받아 삭제합니다."
    )
    @ApiImplicitParam(name="heartId", dataType = "integer", value = "좋아요 식별자")
    @DeleteMapping("/{heartId}/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyCommentHeart(@PathVariable Long heartId, @PathVariable Long commentId) {
        heartService.destroyCommentHeart(heartId, commentId);
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
