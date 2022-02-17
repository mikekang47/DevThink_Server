package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.ReplyHeartService;
import com.devthink.devthink_server.domain.ReplyHeart;
import com.devthink.devthink_server.dto.ReplyHeartResponseData;
import com.devthink.devthink_server.security.UserAuthentication;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/replies/hearts")
public class ReplyHeartController {

    private final ReplyHeartService replyHeartService;

    public ReplyHeartController(ReplyHeartService replyHeartService) {
        this.replyHeartService = replyHeartService;
    }

    @ApiOperation(
            value ="답글 좋아요 생성",
            notes = "좋아요를 생성하려는 답글의 식별자와 사용자 토큰으로 새로운 답글 좋아요를 생성하여, 그 정보를 리턴합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = ReplyHeartResponseData.class
    )
    @PostMapping("/{replyId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public ReplyHeartResponseData createReplyHeart(@PathVariable Long replyId, UserAuthentication userAuthentication) {
        Long userId = userAuthentication.getUserId();
        ReplyHeart replyHeart = replyHeartService.create(replyId, userId);
        return getReplyHeartData(replyHeart);
    }

    @ApiOperation(
            value = "답글 좋아요 취소",
            notes = "좋아요를 생성하려는 답글의 식별자와 사용자 토큰으로 답글 좋아요를 취소하고 204을 반환합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = ReplyHeartResponseData.class
    )
    @DeleteMapping("/{replyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void destroyReplyHeart(@PathVariable Long replyId, UserAuthentication authentication) {
        Long userId = authentication.getUserId();
        replyHeartService.destroy(replyId, userId);

    }

    private ReplyHeartResponseData getReplyHeartData(ReplyHeart replyHeart) {
        return ReplyHeartResponseData.builder()
                .id(replyHeart.getId())
                .userId(replyHeart.getUser().getId())
                .replyId(replyHeart.getReply().getId())
                .build();


    }

}
