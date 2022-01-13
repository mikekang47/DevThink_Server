package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review save(Review review);
}
