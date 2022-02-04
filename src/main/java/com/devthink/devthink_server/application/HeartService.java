package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.*;
import com.devthink.devthink_server.errors.*;
import com.devthink.devthink_server.infra.HeartRepository;
import com.devthink.devthink_server.infra.PostRepository;
import com.devthink.devthink_server.infra.ReviewRepository;
import com.devthink.devthink_server.infra.UserRepository;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final Mapper mapper;

    // TODO
    // GET -> 좋아요가 눌렸는지 아닌지 확인 -> 완료
    // POST -> 좋아요 생성
    // DELETE -> 좋아요 취소

    public Heart getHeart(Long id) {
        return heartRepository.findById(id).orElseThrow(() -> new HeartNotFoundException(id));

    }

    public Heart createPostHeart(Long postId, Long userId) {
        User user = findUser(userId);
        Post post = findPost(postId);
        Heart heart = Heart.builder().user(user).post(post).build();
        return heartRepository.save(heart);
    }

    public Heart createCommentHeart(Long commentId, Long userId) {
        User user = findUser(userId);
        Comment comment = findComment(commentId);
        Heart heart = Heart.builder().user(user).comment(comment).build();
        return heartRepository.save(heart);
    }

    public Heart createReviewHeart(Long reviewId, Long userId) {
        User user = findUser(userId);
        Review review = findReview(reviewId);
        Heart heart = Heart.builder().user(user).review(review).build();
        return heartRepository.save(heart);
    }

    public void destroyPostHeart(Long id) {
        heartRepository.deleteById(id);
    }

    private User findUser(Long userId) {
        return userRepository.findByIdAndDeletedIsFalse(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostIdNotFoundException(postId));
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    private Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }
}
