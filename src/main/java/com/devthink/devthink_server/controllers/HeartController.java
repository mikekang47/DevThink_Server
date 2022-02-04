package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.HeartService;
import com.devthink.devthink_server.domain.*;
import com.devthink.devthink_server.dto.HeartCommentResponseData;
import com.devthink.devthink_server.dto.HeartPostResponseData;
import com.devthink.devthink_server.dto.HeartReviewResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hearts")
public class HeartController {

    private final HeartService heartService;

    public HeartController(HeartService heartService) {
        this.heartService = heartService;
    }

    @GetMapping("/{id}")
    public HeartPostResponseData checkHeart(@PathVariable Long id) {
        Heart heart = heartService.getHeart(id);
        return getPostHeartData(heart);
    }

    @PostMapping("/post/{postId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public HeartPostResponseData createPostHeart(@PathVariable Long postId, @PathVariable Long userId) {
        Heart heart = heartService.createPostHeart(postId, userId);
        return getPostHeartData(heart);
    }

    @PostMapping("/comment/{commentId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public HeartCommentResponseData createCommentHeart(@PathVariable Long commentId, @PathVariable Long userId) {
        Heart heart = heartService.createCommentHeart(commentId, userId);
        return getCommentHeartData(heart);
    }

    @PostMapping("/review/{reviewtId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public HeartReviewResponseData createReviewHeart(@PathVariable Long reviewId, @PathVariable Long userId) {
        Heart heart = heartService.createReviewHeart(reviewId, userId);
        return getReviewHeartData(heart);
    }

    @DeleteMapping("/post/{heartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long heartId) {
        heartService.destroyPostHeart(heartId);
    }

    private HeartReviewResponseData getReviewHeartData(Heart heart) {
        return HeartReviewResponseData.builder()
                .id(heart.getId())
                .userId(heart.getUser().getId())
                .reviewId(heart.getReview().getId())
                .build();
    }

    private HeartPostResponseData getPostHeartData(Heart heart) {
        return HeartPostResponseData.builder()
                .id(heart.getId())
                .userId(heart.getUser().getId())
                .postId(heart.getPost().getId())
                .build();
    }

    private HeartCommentResponseData getCommentHeartData(Heart heart) {
        return HeartCommentResponseData.builder()
                .id(heart.getId())
                .userId(heart.getUser().getId())
                .commentId(heart.getComment().getId())
                .build();
    }
    
}
