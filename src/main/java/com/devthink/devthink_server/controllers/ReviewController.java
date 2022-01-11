package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.ReviewService;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String createReview(@RequestBody ReviewRequestDto reviewRequestDto){
        User user = null; //TODO: user_id 로 User 가져오기
        Book book = null; //TODO: book_id 로 Book 가져오기
        return reviewService.createReview(user, book, reviewRequestDto);
    }

}
