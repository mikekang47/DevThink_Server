package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.HeartService;
import com.devthink.devthink_server.domain.*;
import com.devthink.devthink_server.dto.HeartData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class HeartController {

    private final HeartService heartService;

    public HeartController(HeartService heartService) {
        this.heartService = heartService;
    }

    
    @PostMapping("/postLike")
    @ResponseStatus(HttpStatus.CREATED)
    public HeartData createPostHeart(@RequestBody Post post, @RequestBody User user) {
        Heart heart = heartService.createPostHeart(post, user);
        return getPostHeartData(heart);

    }

    @PostMapping("/commentLike")
    @ResponseStatus(HttpStatus.CREATED)
    public HeartData createCommentHeart(@RequestBody Comment comment, @RequestBody User user) {
        Heart heart = heartService.createCommentHeart(comment, user);
        return getCommentHeartData(heart);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyLike(@PathVariable Long id) {
        heartService.deletePostHeart(id);
    }



    private HeartData getPostHeartData(Heart heart) {
        return HeartData.builder()
                .userId(heart.getUserId())
                .postId(heart.getPostId())
                .build();
    }

    private HeartData getCommentHeartData(Heart heart) {
        return HeartData.builder()
                .userId(heart.getUserId())
                .commentId(heart.getCommentId())
                .build();
    }
}
