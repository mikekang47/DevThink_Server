package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CommentService;
import com.devthink.devthink_server.domain.Comment;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.CommentRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 모든 Comment를 조회합니다.
     * @return
     */
    @GetMapping
    public List<Comment> getComments() {
        return commentService.getComments();
    }

    /**
     * 새로운 Comment를 생성합니다.
     * @return
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String createComment(@RequestBody CommentRequestDto commentRequestDto){
        User user = null; //TODO: user_id 로 User 가져오기
        Review review = null; //TODO: review_id 로 Review 가져오기
        return commentService.createComment(user, review, commentRequestDto);
    }
}
