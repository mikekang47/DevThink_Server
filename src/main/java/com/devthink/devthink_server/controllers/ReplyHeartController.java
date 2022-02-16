package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.ReplyHeartService;
import com.devthink.devthink_server.domain.ReplyHeart;
import com.devthink.devthink_server.dto.ReplyHeartResponseData;
import com.devthink.devthink_server.security.UserAuthentication;

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

    @PostMapping("/{replyId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public ReplyHeartResponseData createReplyHeart(@PathVariable Long replyId, UserAuthentication userAuthentication) {
        Long userId = userAuthentication.getUserId();
        ReplyHeart replyHeart = replyHeartService.create(replyId, userId);
        return getReplyHeartData(replyHeart);
    }

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
