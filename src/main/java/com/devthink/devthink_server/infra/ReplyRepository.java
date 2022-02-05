package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAll();

    Optional<Reply> findById(Long id);

    Reply save(Reply comment);

    List<Reply> findByUserId(Long userIdx);

    List<Reply> findByCommentId(@Param(value = "commentId") Long commentId);


}
