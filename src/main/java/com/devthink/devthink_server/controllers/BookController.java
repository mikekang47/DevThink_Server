package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.BookService;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.dto.BookListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * 책 리스트를 전달된 pageable 기준에 따라 가져온다.
     * [GET] /books/list?page= &size= &sort= ,정렬방식
     * @return 평점 높은 순으로 정렬된 책 리스트
     */
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BookListResponseDto list(Pageable pageable) {
        return BookListResponseDto.builder()
                .bestBook(bookService.getBestBookByReviewCnt())
                .bookList(bookService.list(pageable))
                .build();
    }

}
