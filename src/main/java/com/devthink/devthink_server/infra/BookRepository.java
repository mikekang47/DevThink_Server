package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book save(Book book);

    Optional<Book> getBookByIsbn(int isbn);

    List<Book> findAllOrderByScoreAvgDesc(Pageable pageable); // 평점 높은 순
    List<Book> findAllOrderByScoreAvg(Pageable pageable); // 평점 낮은 순
    List<Book> findAllOrderByReviewCntDesc(Pageable pageble); // 인기순 (리뷰 수 순)
    List<Book> findAllOrderByCreateAtDesc(Pageable pageable); // 최신순
    Optional<Book> findTopByReviewCnt(); // 베스트 리뷰

    @Query(value = "select round(avg(score),2) from Review where book_id = :id and deleted = false")
    BigDecimal calcScoreAvg(@Param("id") long id);
}
