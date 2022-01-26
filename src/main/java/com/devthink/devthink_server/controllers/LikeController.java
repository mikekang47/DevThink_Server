package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.LikeService;
import com.devthink.devthink_server.domain.Like;
import com.devthink.devthink_server.dto.LikeData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/{id}")
    public LikeData detail(@PathVariable Long id) {
        Like like = likeService.getLike(id);
        return getLikeData(like);
    }

    @PostMapping
    public LikeData create(@RequestBody Long userId, @RequestBody Long postId) {
        Like like = likeService.createLike(userId, postId);
        return getLikeData(like);

    }
    private LikeData getLikeData(Like like) {
        return LikeData.builder()
                .userId(like.getUserId())
                .postId(like.getPostId())
                .build();
    }
}
