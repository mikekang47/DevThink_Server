package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.CommentHeart;
import com.devthink.devthink_server.domain.ReviewHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
    CommentHeart save(ReviewHeart reviewHeart);

    Optional<CommentHeart> findByCommentId(Long commentId);

    boolean existsByCommentIdAndUserId(Long commentId, Long userId);
}
