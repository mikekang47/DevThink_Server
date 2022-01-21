package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAll();
    Optional<Comment> findById(Long id);
    Comment save(Comment comment);
    List<Comment> findByUser_Id(@Param(value = "userId") Long userId);
    List<Comment> findByPost_Id(@Param(value = "postIdx") Long postId);
    List<Comment> findByReview_Id(@Param(value = "reviewId") Long reviewId);
}
