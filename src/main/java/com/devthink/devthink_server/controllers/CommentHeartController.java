package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CommentHeartService;
import com.devthink.devthink_server.domain.CommentHeart;
import com.devthink.devthink_server.dto.CommentHeartResponseData;
import com.devthink.devthink_server.security.UserAuthentication;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments/hearts")
public class CommentHeartController {
    private final CommentHeartService commentHeartService;

    public CommentHeartController(CommentHeartService commentHeartService) {
        this.commentHeartService = commentHeartService;
    }

    /**
     * 좋아요를 생성하려는 댓글의 식별자와 사용자 토큰으로 새로운 댓글 좋아요를 생성하여, 그 정보를 리턴합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.
     * @param commentId 좋아요를 생성하려는 댓글의 식별자
     * @return 생성된 좋아요의 정보
     */
    @ApiOperation(
            value = "댓글 좋아요 생성",
            notes = "좋아요를 생성하려는 댓글의 식별자와 사용자 토큰으로 새로운 댓글 좋아요를 생성하여, 생성된 좋아요의 정보를 리턴합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = CommentHeartResponseData.class
    )
    @PostMapping("/{commentId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentHeartResponseData createCommentHeart(@PathVariable Long commentId, UserAuthentication userAuthentication) {
        Long userId = userAuthentication.getUserId();
        CommentHeart commentHeart = commentHeartService.createCommentHeart(commentId, userId);
        return getCommentHeartData(commentHeart);
    }

    /**
     * 좋아요를 취소하고자 하는 댓글의 식별자를 받아 좋아요를 취소합니다.
     * @param commentId 좋아요를 취소하고자 하는 댓글의 식별자
     */
    @ApiOperation(
            value= "댓글 좋아요 삭제(좋아요 취소)",
            notes= "좋아요를 취소하고자 하는 댓글의 식별자를 받아 좋아요를 취소합니다. 헤더에 사용자 토큰 주입을 필요로 합니다."
    )
    @ApiImplicitParam(name="commentId", value = "좋아요를 취소하고자 하는 댓글의 식별자", dataType = "integer")
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void destroyCommentHeart(@PathVariable Long commentId) {
        commentHeartService.destroyCommentHeart(commentId);
    }

    /**
     * 댓글과 관련한 좋아요의 정보를 받아서 dto로 변환후 리턴합니다.
     * @param commentHeart 변환할 좋아요의 정보
     * @return dto로 변환된 좋아요 정보
     */
    private CommentHeartResponseData getCommentHeartData(CommentHeart commentHeart) {
        return CommentHeartResponseData.builder()
                .id(commentHeart.getId())
                .userId(commentHeart.getUser().getId())
                .commentId(commentHeart.getComment().getId())
                .build();
    }
}
