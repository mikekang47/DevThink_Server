package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Comment;
import com.devthink.devthink_server.domain.CommentRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaCommentRepository
        extends CommentRepository, CrudRepository<Comment, Long> {
    List<Comment> findAll();
}
