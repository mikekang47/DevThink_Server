package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.BookService;
import com.devthink.devthink_server.application.ReviewService;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReviewRequestDto;
import com.devthink.devthink_server.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final BookService bookService;

    /**
     * 리뷰 등록 API
     * [POST] /reviews
     * @param reviewRequestDto (userId, bookIsbn, content, score)
     * @return reviewId (새로 생성된 리뷰 아이디)
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String createReview(@RequestBody ReviewRequestDto reviewRequestDto){
        //TODO: user_id 로 User 가져오기
        User user = new User();
        user.setId(reviewRequestDto.getUserId());
        // Isbn 으로 Book 가져오기
        Book book = bookService.getBookByIsbn(reviewRequestDto.getBookIsbn());
        String reviewId = reviewService.createReview(user, book, reviewRequestDto);
        return reviewId;
    }


}
