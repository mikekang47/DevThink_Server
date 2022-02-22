package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CommentService;
import com.devthink.devthink_server.application.PostService;
import com.devthink.devthink_server.application.ReviewService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.CommentModificationData;
import com.devthink.devthink_server.dto.CommentPostRequestData;
import com.devthink.devthink_server.dto.CommentReviewRequestData;
import com.devthink.devthink_server.dto.CommentResponseData;
import com.devthink.devthink_server.security.UserAuthentication;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @ApiIgnore
    public List<CommentResponseData> getComments() {
        return commentService.getComments();
    }

    /**
     * 특정 사용자가 Review에 등록한 Comment를 모두 조회합니다.
     * @return 특정 사용자가 작성한 Comment 리스트
     */
    @ApiOperation(value = "사용자의 리뷰 댓글 조회",
            notes = "특정 사용자가 리뷰에 등록한 댓글을 모두 조회합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = List.class)
    @GetMapping("/user/review")
    @PreAuthorize("isAuthenticated()")
    public List<CommentResponseData> getUserReviewComments(UserAuthentication userAuthentication) {
        Long userIdx = userAuthentication.getUserId();
        return commentService.getUserReviewComments(userIdx);
    }

    /**
     * 특정 사용자가 Post에 등록한 Comment를 모두 조회합니다.
     * @return 특정 사용자가 작성한 Comment 리스트
     */
    @ApiOperation(value = "사용자의 게시글 댓글 조회",
            notes = "특정 사용자가 게시글에 등록한 댓글을 모두 조회합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = List.class)
    @GetMapping("/user/post")
    @PreAuthorize("isAuthenticated()")
    public List<CommentResponseData> getUserPostComments(UserAuthentication userAuthentication) {
        Long userIdx = userAuthentication.getUserId();
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
     * @param commentReviewRequestData 생성하려는 Review Comment의 요청 정보
     * @return 생성된 Comment
     */
    @ApiOperation(value = "리뷰 댓글 등록",
            notes = "입력된 댓글 정보로 리뷰에 새로운 댓글을 등록합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = String.class)
    @PostMapping("/review")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public CommentResponseData createReviewComment(@Valid @RequestBody CommentReviewRequestData commentReviewRequestData,
                                                   UserAuthentication authentication) {
        Long reviewId = commentReviewRequestData.getReviewId();
        // userId 값을 통하여 userRepository에서 User를 가져옵니다.
        User user = userService.getUser(authentication.getUserId());
        // reviewId 값을 통하여 reviewRepository에서 Review를 가져옵니다.
        Review review = reviewService.getReviewById(reviewId);
        return commentService.createReviewComment(user, review, commentReviewRequestData.getContent());
    }

    /**
     * 입력된 comment 정보로 Post에 등록할 새로운 Comment를 생성합니다.
     * @param commentPostRequestData 생성하려는 Post Comment의 요청 정보
     * @return 생성된 Comment
     */
    @ApiOperation(value = "게시글 댓글 등록",
            notes = "입력된 댓글 정보로 게시글에 새로운 댓글을 등록합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = String.class)
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public CommentResponseData createPostComment(@Valid @RequestBody CommentPostRequestData commentPostRequestData,
                                                 UserAuthentication authentication){
        Long postId = commentPostRequestData.getPostId();
        // userId 값을 통하여 userRepository에서 User를 가져옵니다.
        User user = userService.getUser(authentication.getUserId());
        // postId 값을 통하여 postRepository에서 Post를 가져옵니다.
        Post post = postService.getPostById(postId);
        return commentService.createPostComment(user, post, commentPostRequestData.getContent());
    }

    /**
     * commentId를 통하여 기존의 Comment를 수정합니다.
     * @return Comment 수정 결과 response
     */
    @ApiOperation(value = "댓글 수정",
            notes = "입력된 댓글의 식별자로 수정할 댓글을 찾아, 주어진 데이터로 댓글의 정보를 갱신합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = CommentResponseData.class)
    @ApiImplicitParam(name = "commentId", value = "수정할 댓글의 식별자")
    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public CommentResponseData updateComment(@PathVariable("commentId") Long commentId,
                                             @Valid @RequestBody CommentModificationData commentModificationData){
        return commentService.updateComment(commentId, commentModificationData.getContent());
    }

    /**
     * commentId를 통하여 기존의 Comment를 삭제합니다.
     * @param commentId 삭제할 Comment의 식별자
     */
    @ApiOperation(value = "댓글 삭제", notes = "입력된 댓글의 식별자로 댓글을 찾아 삭제합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.")
    @ApiImplicitParam(name = "commentId", value = "삭제할 Comment의 식별자")
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
    }

}
