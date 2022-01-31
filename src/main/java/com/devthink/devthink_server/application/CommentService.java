package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Comment;
import com.devthink.devthink_server.errors.CommentNotFoundException;
import com.devthink.devthink_server.errors.ReviewNotFoundException;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.devthink.devthink_server.infra.CommentRepository;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.CommentRequestDto;
import com.devthink.devthink_server.infra.ReviewRepository;
import com.devthink.devthink_server.infra.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          ReviewRepository reviewRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    /**
     * 모든 Comment를 조회합니다.
     * @return
     */
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    /**
     * 특정 Comment를 조회합니다.
     * @return 조회된 Comment 또는 {@literal Optional#empty()}
     */
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    /**
     * 특정 사용자의 Comment를 조회합니다.
     * @return 특정 사용자가 작성한 Comment 리스트
     */
    public List<Comment> getUserComments(Long userIdx) {
        return commentRepository.findAllByUserId(userIdx);
    }

    /**
     * 특정 Post의 Comment를 조회합니다.
     * @return 특정 게시물에 작성된 Comment 리스트
     */
    public List<Comment> getPostComments(Long postIdx) {
        return commentRepository.findByPostId(postIdx);
    }

    /**
     * 특정 Review의 Comment를 조회합니다.
     * @return 특정 리뷰에 작성된 Comment 리스트
     */
    public List<Comment> getReviewComments(Long reviewIdx) {
        return commentRepository.findByReviewId(reviewIdx);
    }

    /**
     * 입력된 comment 정보로 새로운 Comment를 생성합니다.
     * @param commentRequestDto Comment를 생성하려고 하는 request
     * @return 생성된 Comment의 id 값
     */
    public String createComment(CommentRequestDto commentRequestDto) {
        // request상에 userId 값이 들어있는지 확인합니다.
        Long userId = Optional.ofNullable(commentRequestDto.getUserId())
                                .orElseThrow(()->new IllegalArgumentException("The userId cannot be null"));
        // request상에 reviewId 값이 들어있는지 확인합니다.
        Long reviewId = Optional.ofNullable(commentRequestDto.getReviewId())
                .orElseThrow(()->new IllegalArgumentException("The reviewId cannot be null"));

        // userId 값을 통하여 userRepository에서 User를 가져옵니다.
        User user = findUser(userId);
        // reviewId 값을 통하여 reviewRepository에서 Review를 가져옵니다.
        Review review = findReview(reviewId);

        // commentRepository에 새로운 댓글을 생성합니다.
        Comment comment = commentRepository.save(
                Comment.builder()
                        .user(user)
                        .review(review)
                        .content(commentRequestDto.getContent())
                        .build()
        );
        return comment.getId().toString();
    }

    /**
     * commentId를 통하여 기존의 Comment를 수정합니다.
     * @param commentId 수정할 Comment의 식별자
     * @param content 수정할 content 내용
     */
    public Comment updateComment(Long commentId, String content) {
        Comment comment = getComment(commentId);
        comment.setContent(content);
    }


    /**
     * 전달받은 사용자의 식별자를 이용하여 사용자를 DB에서 찾고, 없으면 Error를 보냅니다.
     * @param id 찾고자 하는 사용자의 식별자
     * @return 찾았을 경우 사용자를 반환. 찾지 못했을 경우 error를 반환.
     */
    private User findUser(Long id) {
        return userRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    //리뷰 조회
    private Review findReview(Long id) {
        return reviewRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }
}
