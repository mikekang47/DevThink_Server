package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Heart;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.ReviewHeart;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.errors.HeartAlreadyExistsException;
import com.devthink.devthink_server.errors.HeartNotFoundException;
import com.devthink.devthink_server.errors.ReviewNotFoundException;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.devthink.devthink_server.infra.ReviewRepository;
import com.devthink.devthink_server.infra.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewHeartService {

    private final ReviewHeartRepository reviewHeartRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewHeartService(ReviewHeartRepository reviewHeartRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.reviewHeartRepository = reviewHeartRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    /**
     * 전달받은 리뷰 객체와 사용자 객체로 좋아요를 생성합니다.
     *
     * @param reviewId 리뷰 식별자
     * @param userId   사용자 식별자
     * @return 생성된 댓글 좋아요 객체
     */
    public ReviewHeart createReviewHeart(Long reviewId, Long userId) {
        Review review = findReview(reviewId);
        User user = findUser(userId);

        if(reviewHeartRepository.existsByReviewIdAndUserId(reviewId, userId)) {
            throw new HeartAlreadyExistsException();
        }

        review.updateHeart(review.getHeartCnt() + 1);
        ReviewHeart reviewHeart = ReviewHeart.builder()
                .user(user)
                .review(review)
                .build();
        return reviewHeartRepository.save(reviewHeart);
    }

    public void destroyReviewHeart(Long reviewId) {
        Review review = findReview(reviewId);
        ReviewHeart reviewHeart = reviewHeartRepository.findByReviewId(reviewId)
                .orElseThrow(HeartNotFoundException::new);

        review.updateHeart(review.getHeartCnt() - 1);
        reviewHeartRepository.deleteById(reviewHeart.getId());
    }

    private User findUser(Long userId) {
        return userRepository.findByIdAndDeletedIsFalse(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }
}
