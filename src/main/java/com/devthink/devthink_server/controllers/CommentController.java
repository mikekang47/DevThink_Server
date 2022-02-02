package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CommentService;
import com.devthink.devthink_server.application.ReviewService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.CommentRequestData;
import com.devthink.devthink_server.dto.CommentResponseData;
import com.devthink.devthink_server.errors.PostCommentBadRequestException;
import com.devthink.devthink_server.errors.ReviewCommentBadRequestException;
import com.devthink.devthink_server.service.PostService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;
    private final ReviewService reviewService;


    public CommentController(CommentService commentService,
                             UserService userService,
                             PostService postService,
                             ReviewService reviewService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
        this.reviewService = reviewService;
    }

    /**
     * 모든 Comment를 조회합니다.
     * @return DB 상에 존재하는 모든 Comment 리스트
     */
    @ApiOperation(value = "전체 댓글 조회", notes = "모든 댓글을 조회합니다.", response = List.class)
    @GetMapping
    public List<CommentResponseData> getComments() {
        return commentService.getComments();
    }

    /**
     * 특정 사용자가 Review에 등록한 Comment를 모두 조회합니다.
     * @param userIdx 댓글을 조회할 사용자의 식별자
     * @return 특정 사용자가 작성한 Comment 리스트
     */
    @ApiOperation(value = "사용자의 리뷰 댓글 조회",
            notes = "특정 사용자가 리뷰에 등록한 댓글을 모두 조회합니다.",
            response = List.class)
    @ApiImplicitParam(name = "userIdx", value = "댓글을 조회할 사용자의 식별자")
    @GetMapping("/user/{userIdx}/review")
    public List<CommentResponseData> getUserReviewComments(@PathVariable("userIdx") Long userIdx) {
        return commentService.getUserReviewComments(userIdx);
    }

    /**
     * 특정 사용자가 Post에 등록한 Comment를 모두 조회합니다.
     * @param userIdx 댓글을 조회할 사용자의 식별자
     * @return 특정 사용자가 작성한 Comment 리스트
     */
    @ApiOperation(value = "사용자의 게시글 댓글 조회",
            notes = "특정 사용자가 게시글에 등록한 댓글을 모두 조회합니다.",
            response = List.class)
    @ApiImplicitParam(name = "userIdx", value = "댓글을 조회할 사용자의 식별자")
    @GetMapping("/user/{userIdx}/post")
    public List<CommentResponseData> getUserPostComments(@PathVariable("userIdx") Long userIdx) {
        return commentService.getUserPostComments(userIdx);
    }

    /**
     * 특정 Post의 Comment를 조회합니다.
     * @param postIdx 조회할 대상 게시글의 식별자
     * @return 특정 게시물에 작성된 Comment 리스트
     */
    @ApiOperation(value = "게시글 댓글 조회", notes = "특정 게시글의 댓글을 조회합니다.", response = List.class)
    @ApiImplicitParam(name = "postIdx", value = "조회할 대상 게시글의 식별자")
    @GetMapping("/post/{postIdx}")
    @ApiIgnore
    public List<CommentResponseData> getPostComments(@PathVariable("postIdx") Long postIdx) {
        return commentService.getPostComments(postIdx);
    }

    /**
     * 특정 Review의 Comment를 조회합니다.
     * @param reviewIdx 조회할 대상 리뷰의 식별자
     * @return 특정 리뷰에 작성된 Comment 리스트
     */
    @ApiOperation(value = "리뷰 댓글 조회", notes = "특정 리뷰의 댓글을 조회합니다.", response = List.class)
    @ApiImplicitParam(name = "reviewIdx", value = "조회할 대상 리뷰의 식별자")
    @GetMapping("/review/{reviewIdx}")
    @ApiIgnore
    public List<CommentResponseData> getReviewComments(@PathVariable("reviewIdx") Long reviewIdx) {
        return commentService.getReviewComments(reviewIdx);
    }

    /**
     * 입력된 comment 정보로 Review에 등록할 새로운 Comment를 생성합니다.
     * @param commentRequestData 생성하려는 Comment의 요청 정보
     * @return 생성된 Comment
     */
    @ApiOperation(value = "리뷰 댓글 등록", notes = "입력된 댓글 정보로 리뷰에 새로운 댓글을 등록합니다.", response = String.class)
    @PostMapping("/review")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseData createReviewComment(@Valid @RequestBody CommentRequestData commentRequestData){
        Long reviewId = commentRequestData.getReviewId();
        // request상에 reviewId 값이 들어있는지 확인합니다.
        if (reviewId != null) {
            // userId 값을 통하여 userRepository에서 User를 가져옵니다.
            User user = userService.findUser(commentRequestData.getUserId());
            // reviewId 값을 통하여 reviewRepository에서 Review를 가져옵니다.
            Review review = reviewService.getReviewById(reviewId);
            return commentService.createReviewComment(user, review, commentRequestData.getContent());
        } else {
            throw new ReviewCommentBadRequestException();
        }
    }

    /**
     * 입력된 comment 정보로 Post에 등록할 새로운 Comment를 생성합니다.
     * @param commentRequestData 생성하려는 Comment의 요청 정보
     * @return 생성된 Comment
     */
    @ApiOperation(value = "게시글 댓글 등록", notes = "입력된 댓글 정보로 게시글에 새로운 댓글을 등록합니다.", response = String.class)
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseData createPostComment(@Valid @RequestBody CommentRequestData commentRequestData){
        Long postId = commentRequestData.getPostId();
        // request상에 postId 값이 들어있는지 확인합니다.
        if (postId != null) {
            // userId 값을 통하여 userRepository에서 User를 가져옵니다.
            User user = userService.findUser(commentRequestData.getUserId());
            // postId 값을 통하여 postRepository에서 Post를 가져옵니다.
            Post post = postService.getPost(postId);
            return commentService.createPostComment(user, post, commentRequestData.getContent());
        } else {
            throw new PostCommentBadRequestException();
        }
    }

    /**
     * commentId를 통하여 기존의 Comment를 수정합니다.
     * @return Comment 수정 결과 response
     */
    @ApiOperation(value = "댓글 수정",
            notes = "입력된 댓글의 식별자로 수정할 댓글을 찾아, 주어진 데이터로 댓글의 정보를 갱신합니다.",
            response = CommentResponseData.class)
    @ApiImplicitParam(name = "commentId", value = "수정할 댓글의 식별자")
    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseData updateComment(@PathVariable("commentId") Long commentId,
                                             @Valid @RequestBody CommentRequestData commentRequestData){
        return commentService.updateComment(commentId, commentRequestData.getContent());
    }

    /**
     * commentId를 통하여 기존의 Comment를 삭제합니다.
     * @param commentId 삭제할 Comment의 식별자
     */
    @ApiOperation(value = "댓글 삭제", notes = "입력된 댓글의 식별자로 댓글을 찾아 삭제합니다.")
    @ApiImplicitParam(name = "commentId", value = "삭제할 Comment의 식별자")
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
    }

}
