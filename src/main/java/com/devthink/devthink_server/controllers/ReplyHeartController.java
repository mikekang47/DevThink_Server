package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.ReplyHeartService;
import com.devthink.devthink_server.domain.ReplyHeart;
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
    public void createReplyHeart(@PathVariable Long replyId, UserAuthentication userAuthentication) {
        Long userId = userAuthentication.getUserId();
        ReplyHeart replyHeart = replyHeartService.create(replyId, userId);

    }

}
