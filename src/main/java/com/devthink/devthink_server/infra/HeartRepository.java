package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

    Heart save(Heart heart);

    void deleteById(Long id);

    Optional<Heart> findByPostId(Long postId);

    Optional<Heart> findByReviewId(Long reviewId);

    Optional<Heart> findByCommentId(Long commentId);

    boolean existsByReviewIdAndUserId(Long reviewId, Long userId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    Optional<Heart> findByPostIdAndUserId(Long postId, Long userId);
}
