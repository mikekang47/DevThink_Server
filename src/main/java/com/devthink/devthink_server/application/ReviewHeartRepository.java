package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.ReviewHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewHeartRepository extends JpaRepository<ReviewHeart, Long> {
    ReviewHeart save(ReviewHeart reviewHeart);

    Optional<ReviewHeart> findByReviewId(Long reviewId);

    boolean existsByReviewIdAndUserId(Long reviewId, Long userId);
}
