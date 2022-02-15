package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCategory_IdAndTitleContainingAndDeletedIsFalseOrderByIdDesc(Long categoryId, String keyword);

    @Query("select p from Post p where p.category.id = :category order by p.id desc")
    List<Post> findByDeletedIsFalseOrderByIdDesc(Long category);

    Post save(Post post);

    Optional<Post> findByIdAndDeletedIsFalse(Long Id);

    @Query("select p from Post p where (p.createAt between :start and :end) " +
            "and p.category.id = :category and p.deleted = false")
    List<Post> getBestPost(Long category, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
