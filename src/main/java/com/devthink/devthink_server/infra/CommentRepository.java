package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAll();

    Optional<Comment> findById(Long id);

    Comment save(Comment comment);

    List<Comment> findAllByUserIdAndPostIdIsNotNull(@Param(value = "userId") Long userId);

    List<Comment> findAllByUserIdAndReviewIdIsNotNull(@Param(value = "userId") Long userId);

    List<Comment> findByPostId(@Param(value = "postIdx") Long postId);

    List<Comment> findByReviewId(@Param(value = "reviewId") Long reviewId);
}
