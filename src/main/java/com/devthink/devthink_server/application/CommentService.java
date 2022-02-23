package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Comment;
import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.dto.CommentResponseData;
import com.devthink.devthink_server.errors.*;
import com.devthink.devthink_server.infra.CommentRepository;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.infra.PostRepository;
import com.devthink.devthink_server.infra.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;


    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          ReviewRepository reviewRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.reviewRepository = reviewRepository;
    }

    /**
     * 모든 Comment를 조회합니다.
     * @return 조회된 모든 Comment
     */
    public List<CommentResponseData> getComments() {
        return getCommentResponseDataList(commentRepository.findAll());
    }

    /**
     * 특정 Comment를 조회합니다.
     * @param commentId 조회할 댓글의 식별자
     * @return 조회된 Comment
     */
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    /**
     * 특정 사용자가 Review에 등록한 Comment를 모두 조회합니다.
     * @param userIdx 댓글을 조회할 사용자의 식별자
     * @return 특정 사용자가 Review에 작성한 Comment 리스트
     */
    public List<CommentResponseData> getUserReviewComments(Long userIdx) {
        List<Comment> userComments = commentRepository.findByUserIdAndReviewIdIsNotNull(userIdx);
        if (userComments.isEmpty())
            throw new CommentNotFoundException();
        return getCommentResponseDataList(userComments);
    }

    /**
     * 특정 사용자가 Post에 등록한 Comment를 모두 조회합니다.
     * @param userIdx 댓글을 조회할 사용자의 식별자
     * @return 특정 사용자가 Post에 작성한 Comment 리스트
     */
    public List<CommentResponseData> getUserPostComments(Long userIdx) {
        List<Comment> userComments = commentRepository.findByUserIdAndPostIdIsNotNull(userIdx);
        if (userComments.isEmpty())
            throw new CommentNotFoundException();
        return getCommentResponseDataList(userComments);
    }

    /**
     * 특정 Post의 Comment를 조회합니다.
     * @param postIdx 조회할 대상 게시글의 식별자
     * @return 특정 게시물에 작성된 Comment 리스트
     */
    public List<CommentResponseData> getPostComments(Long postIdx) {
        if (!postRepository.existsById(postIdx))
            throw new PostNotFoundException(postIdx);
        List<Comment> postComments = commentRepository.findByPostId(postIdx);
        if (postComments.isEmpty())
            throw new CommentNotFoundException();
        return getCommentResponseDataList(postComments);
    }

    /**
     * 특정 Review의 Comment를 조회합니다.
     * @param reviewIdx 조회할 대상 리뷰의 식별자
     * @return 특정 리뷰에 작성된 Comment 리스트
     */
    public List<CommentResponseData> getReviewComments(Long reviewIdx) {
        if (!reviewRepository.existsById(reviewIdx))
            throw new ReviewNotFoundException(reviewIdx);
        List<Comment> reviewComments = commentRepository.findByReviewId(reviewIdx);
        if (reviewComments.isEmpty())
            throw new CommentNotFoundException();
        return getCommentResponseDataList(reviewComments);
    }

    /**
     * 입력된 comment 정보로 Review에 등록할 새로운 Comment를 생성합니다.
     * @param user Comment를 등록하려고 하는 User
     * @param review Comment와 연결되는 Review
     * @param content Comment의 내용
     * @return 생성된 Comment의 결과 정보
     */
    public CommentResponseData createReviewComment(User user, Review review, String content) {
        // commentRepository에 새로운 댓글을 생성합니다.
        return commentRepository.save(
                Comment.builder()
                .user(user)
                .review(review)
                .content(content)
                .build()
        ).toCommentResponseData();
    }

    /**
     * 입력된 comment 정보로 Post에 등록할 새로운 Comment를 생성합니다.
     * @param user Comment를 등록하려고 하는 User
     * @param post Comment와 연결되는 Post
     * @param content Comment의 내용
     * @return 생성된 Comment의 결과 정보
     */
    public CommentResponseData createPostComment(User user, Post post, String content) {
        // commentRepository에 새로운 댓글을 생성합니다.
        return commentRepository.save(
                Comment.builder()
                .user(user)
                .post(post)
                .content(content)
                .build()
        ).toCommentResponseData();
    }

    /**
     * commentId를 통하여 기존의 Comment를 수정합니다.
     * @param commentId 수정할 Comment의 식별자
     * @param content 수정할 content 내용
     */
    public CommentResponseData updateComment(Long commentId, String content) {
        Comment comment = getComment(commentId);
        comment.setContent(content);
        return commentRepository.save(comment).toCommentResponseData();
    }

    /**
     * commentId를 통하여 기존의 Comment를 삭제합니다.
     * @param commentId 삭제할 Comment의 식별자
     */
    public void deleteComment(Long commentId) {
        getComment(commentId);
        commentRepository.deleteById(commentId);
    }


    /**
     * entity List를 받아 dto List 데이터로 변환하여 반환합니다.
     * @param comments entity List
     * @return 입력된 dto 데이터로 변환된 list
     */
    private List<CommentResponseData> getCommentResponseDataList(List<Comment> comments) {
        List<CommentResponseData> commentResponseData = new ArrayList<>();

        for (Comment comment : comments)
            commentResponseData.add(comment.toCommentResponseData());
        return commentResponseData;
    }
}
