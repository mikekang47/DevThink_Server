package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.LikeService;
import com.devthink.devthink_server.domain.Comment;
import com.devthink.devthink_server.domain.Like;
import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.LikeData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    
    @PostMapping("/postLike")
    @ResponseStatus(HttpStatus.CREATED)
    public LikeData createPostLike(@RequestBody Post post, @RequestBody User user) {
        Like like = likeService.createPostLike(post, user);
        return getPostLikeData(like);

    }

    @PostMapping("/commentLike")
    @ResponseStatus(HttpStatus.CREATED)
    public LikeData createCommentLike(@RequestBody Comment comment, @RequestBody User user) {
        Like like = likeService.createCommentLike(comment, user);
        return getCommentLikeData(like);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyLike(@PathVariable Long id) {
        likeService.deletePostLike(id);
    }


    private LikeData getPostLikeData(Like like) {
        return LikeData.builder()
                .userId(like.getUserId())
                .postId(like.getPostId())
                .build();
    }

    private LikeData getCommentLikeData(Like like) {
        return LikeData.builder()
                .userId(like.getUserId())
                .commentId(like.getCommentId())
                .build();
    }
}
