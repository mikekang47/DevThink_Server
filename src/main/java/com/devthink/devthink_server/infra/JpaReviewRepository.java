package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.ReviewRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReviewRepository extends ReviewRepository, JpaRepository<Review, Long> {
    Review save(Review review);
}
