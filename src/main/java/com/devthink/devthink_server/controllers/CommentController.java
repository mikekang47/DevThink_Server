package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CommentService;
import com.devthink.devthink_server.domain.Comment;
import com.devthink.devthink_server.dto.CommentRequestDto;
import com.devthink.devthink_server.dto.CommentResponseDto;
import com.devthink.devthink_server.errors.CommentContentEmptyException;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     * @return DB 상에 존재하는 모든 Comment 리스트
     */
    @ApiOperation(value = "전체 댓글 조회", notes = "모든 댓글을 조회합니다.", response = List.class)
    @GetMapping
    public List<Comment> getComments() {
        return commentService.getComments();
    }


    /**
     * 특정 사용자의 Comment를 조회합니다.
     * @return 특정 사용자가 작성한 Comment 리스트
     */
    @ApiOperation(value = "사용자 댓글 조회", notes = "특정 사용자의 댓글을 조회합니다.", response = List.class)
    @GetMapping("/user/{userIdx}")
    public List<Comment> getUserComments(@PathVariable("userIdx") Long userIdx) {
        return commentService.getUserComments(userIdx);
    }

    /**
     * 특정 Post의 Comment를 조회합니다.
     * @return 특정 게시물에 작성된 Comment 리스트
     */
    @ApiOperation(value = "게시글 댓글 조회", notes = "특정 게시글의 댓글을 조회합니다.", response = List.class)
    @GetMapping("/post/{postIdx}")
    public List<Comment> getPostComments(@PathVariable("postIdx") Long postIdx) {
        return commentService.getPostComments(postIdx);
    }

    /**
     * 특정 Review의 Comment를 조회합니다.
     * @return 특정 리뷰에 작성된 Comment 리스트
     */
    @ApiOperation(value = "리뷰 댓글 조회", notes = "특정 리뷰의 댓글을 조회합니다.", response = List.class)
    @GetMapping("/review/{reviewIdx}")
    public List<Comment> getReviewComments(@PathVariable("reviewIdx") Long reviewIdx) {
        return commentService.getReviewComments(reviewIdx);
    }

    /**
     * 입력된 comment 정보로 새로운 Comment를 생성합니다.
     * @return 생성된 Comment의 id 값
     */
    @ApiOperation(value = "댓글 등록", notes = "입력된 댓글 정보로 새로운 댓글을 등록합니다.", response = String.class)
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String createComment(@Valid @RequestBody CommentRequestDto commentRequestDto){
        return commentService.createComment(commentRequestDto);
    }


    /**
     * commentId를 통하여 기존의 Comment를 수정합니다.
     * @return Comment 수정 결과 response
     */
    @ApiOperation(value = "댓글 수정",
            notes = "입력된 댓글의 식별자로 수정할 댓글을 찾아, 주어진 데이터로 댓글의 정보를 갱신합니다.",
            response = CommentResponseDto.class)
    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto updateComment(@PathVariable("commentId") Long commentId,
                                            @Valid @RequestBody CommentRequestDto commentRequestDto){
        String content = commentRequestDto.getContent();
        if (content.isBlank())      // 입력받은 request에서 content가 공란인지 확인합니다.
            throw new CommentContentEmptyException();

        Comment comment = commentService.getComment(commentId);
        commentService.updateComment(comment, content);
        return comment.toCommentResponseDto();
    }
}
