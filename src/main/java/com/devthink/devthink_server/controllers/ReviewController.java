package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.BookService;
import com.devthink.devthink_server.application.ReviewService;
import com.devthink.devthink_server.common.Error;
import com.devthink.devthink_server.common.ErrorMessage;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReviewRequestDto;
import com.devthink.devthink_server.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createReview(@Valid @RequestBody ReviewRequestDto reviewRequestDto){
        //TODO: userId 로 User 가져오기
        User user = new User();
        user.setId(reviewRequestDto.getUserId());
        // Isbn 으로 Book을 가져온다.
        Book book = bookService.getBookByIsbn(reviewRequestDto.getBookIsbn());
        String id = reviewService.createReview(user, book, reviewRequestDto);
        return id;
    }


    /**
     * 리뷰 상세 조회 API
     * [GET] /reviews/:id
     * @param id (조회할 리뷰 아이디)
     * @return
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReviewResponseDto getReview(@PathVariable("id") Long id){
        Review review = reviewService.getReviewById(id);
        return review.toReviewResponseDto();
    }

    /**
     * 리뷰 내용 수정 API
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
     * 리뷰 점수 수정 API
     * [PATCH] /reviews/:id/score
     * @param id (수정할 리뷰 아이디 )
     * @return review(삭제된 리뷰)
     */
    @PatchMapping("/{id}/score")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponseDto updateScore(@PathVariable("id") Long id, @Valid @RequestBody ReviewRequestDto reviewRequestDto){
        Review review = reviewService.getReviewById(id);
        reviewService.updateScore(review,reviewRequestDto.getScore());
        return review.toReviewResponseDto();
    }

    /**
     * 리뷰 삭제 API
     * [DELETE] /reviews/:id
     * @param id
     * @return review(삭제된 리뷰 객체)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponseDto deleteReview(@PathVariable("id") Long id){
        Review review = reviewService.getReviewById(id);
        reviewService.deleteReview(review);
        return review.toReviewResponseDto();
    }
}
