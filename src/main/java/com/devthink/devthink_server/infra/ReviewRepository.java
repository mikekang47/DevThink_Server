package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review save(Review review);

    boolean existsByBookIdAndUserIdAndDeletedIsFalse(long bookId, long userId);

    Optional<Review> findByIdAndDeletedIsFalse(long reviewId);

    // 주어진 책 id값에 대한 리뷰들의 평점을 계산하여 가져옵니다.
    @Query("select avg(score) from Review where book.id = :id and deleted = false")
    BigDecimal calcScoreAvg(@Param("id") long id);

}
