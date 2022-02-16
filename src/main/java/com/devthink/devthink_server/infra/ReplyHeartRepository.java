package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.ReplyHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyHeartRepository extends JpaRepository<ReplyHeart, Long> {

    boolean existsByReplyIdAndUserId(Long replyId, Long userId);

    ReplyHeart save(ReplyHeart replyHeart);
}
