package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Letter;
import com.devthink.devthink_server.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContaining(String keyword);

    Post save(Post post);


    @Query("select p from Post p where (p.createAt between :start and :end) " +
            "order by p.likeNumber Desc")
    List<Post> getBestPosts(LocalDateTime start, LocalDateTime end, Pageable pageable);

}
