package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.ReplyHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReplyHeartRepository extends JpaRepository<ReplyHeart, Long> {

    boolean existsByReplyIdAndUserId(Long replyId, Long userId);

    ReplyHeart save(ReplyHeart replyHeart);

    Optional<ReplyHeart> findByReplyIdAndUserId(Long replyId, Long userId);
}
