package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    //리뷰 수가 0이 아닌 책 리스트를 pageable 규칙에 따라 가져온다
    Page<Book> findAllByReviewCntNot(int reviewCnt, Pageable pageable);

    // 리뷰 수가 가장 많은 책을 가져온다.
    Optional<Book> findTopByOrderByReviewCntDesc();

    // 주어진 id값에 해당하는 책의 평점을 계산하여 가져온다.
    @Query(value = "select avg(score) from Review where book_id = :id and deleted = false")
    BigDecimal calcScoreAvg(@Param("id") long id);

    // 책 이름에 검색어가 포함 된 책 리스트를 pageable 규칙에 따라 가져온다
    Page<Book> findAllByNameContaining(String search, Pageable pageable);
}
