package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book save(Book book);

    Optional<Book> getBookByIsbn(String isbn);

    //리뷰 수가 0이 아닌 책 리스트를 pageable 규칙에 따라 가져옵니다.
    Page<Book> findAllByReviewCntNot(int reviewCnt, Pageable pageable);

    // 리뷰 수가 가장 많은 책을 가져옵니다.
    Optional<Book> findTopByOrderByReviewCntDesc();

    // 책 이름에 검색어가 포함 된 책 리스트를 pageable 규칙에 따라 가져옵니다.
    Page<Book> findAllByNameContaining(String search, Pageable pageable);

    // start 부터 end 까지 작성 된 리뷰가 가장 많은 5개의 책 리스트를 가져옵니다.
   @Query("select b from Book b join Review r on b.id = r.book.id " +
           "where (r.createAt between :start and :end ) and r.deleted = false " +
           "group by r.book.id " +
           "order by count(r) desc")
    List<Book> findTop5InPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

}
