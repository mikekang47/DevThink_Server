package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.BookService;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.dto.BookListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * 책 리스트를 평점 높은 순으로 조회한다.
     * [GET] /books/list
     * @return 평점 높은 순으로 정렬된 책 리스트
     */
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BookListResponseDto> getBooksOrderByScoreAvgDesc() {
        return bookService.getBooksOrderByScoreAvgDesc()
                .stream()
                .map(Book::toBookListResponseDto)
                .collect(Collectors.toList());
    }

}
