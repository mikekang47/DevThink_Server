package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Category;
import com.devthink.devthink_server.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByIdAndDeletedIsFalse(Long categoryId);

    List<Category> findAllByDeletedIsFalse();
}
