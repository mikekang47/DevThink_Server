package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.application.BookService;
import com.devthink.devthink_server.application.ReviewService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReviewRequestDto;
import com.devthink.devthink_server.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final BookService bookService;
    private final UserService userService;

    /**
     * 입력한 valid한 리뷰 정보를 받아 리뷰를 생성합니다.
     * [POST] /reviews
     * @param reviewRequestDto (userId, bookIsbn, content, score)
     * @return 새로 생성된 리뷰 아이디
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody ReviewRequestDto reviewRequestDto){
        User user = userService.getUser(reviewRequestDto.getUserId());
        Book book = bookService.getBookByIsbn(reviewRequestDto.getBookIsbn());
        String id = reviewService.createReview(user, book, reviewRequestDto);
        return id;
    }


    /**
     * 주어진 id 의 리뷰를 상세 조회합니다.
     * [GET] /reviews/:id
     * @param id (조회할 리뷰 아이디)
     * @return ReviewResponseDto (조회한 리뷰 정보 )
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReviewResponseDto detail(@PathVariable("id") Long id){
        Review review = reviewService.getReviewById(id);
        return review.toReviewResponseDto();
    }


    /**
     * 주어진 id 의 리뷰 내용을 수정합니다.
     * [PATCH] /reviews/:id/content
     * @param id (수정할 리뷰 아이디)
     */
    @PatchMapping("/{id}/content")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponseDto updateContent(@PathVariable("id") Long id, @Valid @RequestBody ReviewRequestDto reviewRequestDto){
        Review review = reviewService.getReviewById(id);
        reviewService.updateContent(review, reviewRequestDto.getContent());
        return review.toReviewResponseDto();
    }


    /**
     * 주어진 id 의 리뷰 점수를 수정합니다.
     * [PATCH] /reviews/:id/score
     * @param id (수정할 리뷰 아이디 )
     * @return ReviewResponseDto (삭제된 리뷰 )
     */
    @PatchMapping("/{id}/score")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponseDto updateScore(@PathVariable("id") Long id, @Valid @RequestBody ReviewRequestDto reviewRequestDto){
        Review review = reviewService.getReviewById(id);
        reviewService.updateScore(review,reviewRequestDto.getScore());
        return review.toReviewResponseDto();
    }


    /**
     * 주어진 id 의 리뷰를 삭제합니다.
     * [DELETE] /reviews/:id
     * @param id
     * @return review(삭제된 리뷰 정보 )
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponseDto destroy(@PathVariable("id") Long id){
        Review review = reviewService.getReviewById(id);
        reviewService.deleteReview(review);
        return review.toReviewResponseDto();
    }

}
