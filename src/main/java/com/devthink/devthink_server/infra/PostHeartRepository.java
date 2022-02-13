package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    PostHeart save(PostHeart postHeart);
}
