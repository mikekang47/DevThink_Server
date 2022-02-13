package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.PostHeartService;
import com.devthink.devthink_server.domain.PostHeart;
import com.devthink.devthink_server.dto.PostHeartResponseData;
import com.devthink.devthink_server.security.UserAuthentication;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/hearts/posts")
@RestController
public class PostHeartController {
    private final PostHeartService postHeartService;

    public PostHeartController(PostHeartService postHeartService) {
        this.postHeartService = postHeartService;
    }

    /**
     * 좋아요를 생성하려는 게시글 객체와 사용자 토큰으로 새로운 좋아요를 생성하여, 그 정보를 리턴합니다.
     * @param postId 좋아요를 생성하려는 게시글의 식별자
     * @header accessToken 사용자의 토큰
     * @return 생성된 좋아요의 정보
     */
    @ApiOperation(
            value= "개시글 좋아요 생성",
            notes = "좋아요를 생성하려는 게시글 식별자와 사용자 토큰으로 새로운 게시글 좋아요를 생성하여, 그 정보를 리턴합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = PostHeartResponseData.class
    )
    @PostMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public PostHeartResponseData createPostHeart(@PathVariable Long postId, UserAuthentication userAuthentication) {
        Long userId = userAuthentication.getUserId();
        PostHeart postHeart = postHeartService.createPostHeart(postId, userId);
        return getPostHeartData(postHeart);
    }

    /**
     * 게시글과 관련한 좋아요의 정보를 받아서 dto로 변환후 리턴합니다.
     * @param postHeart 변환할 좋아요의 정보
     * @return dto로 변환된 좋아요 정보
     */
    private PostHeartResponseData getPostHeartData(PostHeart postHeart) {
        return PostHeartResponseData.builder()
                .id(postHeart.getId())
                .userId(postHeart.getUser().getId())
                .postId(postHeart.getPost().getId())
                .build();
    }
}
