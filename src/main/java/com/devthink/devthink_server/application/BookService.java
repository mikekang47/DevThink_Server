package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.dto.BookDetailResponseData;
import com.devthink.devthink_server.dto.BookRequestData;
import com.devthink.devthink_server.dto.BookResponseData;
import com.devthink.devthink_server.dto.ReviewResponseData;
import com.devthink.devthink_server.errors.BookNotFoundException;
import com.devthink.devthink_server.infra.BookRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    /**
     * 입력된 id 값으로 책의 상세 정보를 조회합니다.
     * @param id
     * @return 조회된 Book 객체
     */
    public BookDetailResponseData getBookDetailById(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return book.toBookDetailResponseData();
    }

    /**
     * Pagination 을 적용한 책 리스트를 가져옵니다.
     * @param pageable
     * @return Page 단위의 책 리스트
     */
    public Page<BookResponseData> getBooks(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAllByReviewCntNot(0, pageable);
        List<BookResponseData> bookResponseData = bookPage
                .stream()
                .map(Book::toBookResponseData)
                .collect(Collectors.toList());
        return new PageImpl<>(bookResponseData, pageable, bookPage.getTotalElements());
    }

    /**
     * 리뷰 수가 가장 많은 책을 가져옵니다.
     * @return BookResponseDTO, 데이터가 없는 경우 null
     */
    public BookResponseData getMostReviewCntBook() {
        if (bookRepository.findTopByOrderByReviewCntDesc().isEmpty()) {
            return null;
        } else {
            return bookRepository.findTopByOrderByReviewCntDesc().get().toBookResponseData();
        }
    }

    /**
     * 책 이름에 검색어가 포함 된 책을 조회합니다.
     * @param search (검색어), pageable
     * @return 조회된 책 리스트
     */
    public Page<BookResponseData> getSearchBooks(String search, Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAllByNameContaining(search, pageable);
        List<BookResponseData> bookResponseData = bookPage
                .stream()
                .map(Book::toBookResponseData)
                .collect(Collectors.toList());
        return new PageImpl<>(bookResponseData, pageable, bookPage.getTotalElements());
    }

}
