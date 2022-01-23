package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.infra.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    /**
     * 입력된 isbn 정보로 Book을 조회하며, 없으면 새로운 Book을 생성합니다.
     * @param isbn 도서 API 에서 가져온 ISBN
     * @return 조회 혹은 생성된 Book 객체
     */
    public Book getBookByIsbn(int isbn){
        Optional<Book> book = bookRepository.getBookByIsbn(isbn);
        if (book.isEmpty()) {
            return createBook(isbn);
        } else {
            return book.get();
        }
    }

    /**
     * 입력된 isbn 정보로 새로운 Book 을 등록합니다.
     * @param isbn 도서 API 에서 가져온 ISBN
     * @return 생성된 Book 객체
     */
    @Transactional
    public Book createBook(int isbn){
        Book book = Book.builder().isbn(isbn).build();
        return bookRepository.save(book);
    }

    /**
     * 평점 높은 순으로 Book List를 가져온다.
     * @return Book List
     */
    public List<Book> getBooksOrderByScoreAvgDesc(Pageable pageable){
        return bookRepository.findAllOrderByScoreAvgDesc(pageable);
    }

}
