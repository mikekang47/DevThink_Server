package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review save(Review review);

    Optional<Review> findByIdAndDeletedIsFalse(long reviewId);

    Optional<Review> findByBookAndUser(Book book, User user);

}
