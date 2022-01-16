package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.BookService;
import com.devthink.devthink_server.application.ReviewService;
import com.devthink.devthink_server.common.Error;
import com.devthink.devthink_server.common.ErrorMessage;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReviewRequestDto;
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
        String reviewId = reviewService.createReview(user, book, reviewRequestDto);
        return reviewId;
    }


    /**
     * 리뷰 상세 조회 API
     * [GET] /reviews/:reviewId
     * @param reviewId (조회할 리뷰 아이디)
     * @return
     */
    @GetMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Object> getReview(@PathVariable("reviewId") Long reviewId){
        Optional<Review> review = reviewService.getReviewById(reviewId);
        if(review.isEmpty()){
            return ResponseEntity.badRequest().body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_REVIEW));
        } else{
            return ResponseEntity.ok().body(review.get().toReviewResponseDto());
        }
    }

    /**
     * 리뷰 내용 수정 API
     * [PATCH] /reviews/:reviewId/content
     * @param reviewId (수정할 리뷰 아이디)
     */
    @PatchMapping("/{reviewId}/content")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateContent(@PathVariable("reviewId") Long reviewId, @Valid @RequestBody ReviewRequestDto reviewRequestDto){
        Optional<Review> review = reviewService.getReviewById(reviewId);
        if(review.isEmpty()){
            return ResponseEntity.badRequest().body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_REVIEW));
        } else{
            reviewService.updateContent(review.get(), reviewRequestDto.getContent());
            return ResponseEntity.ok().body(review.get().toReviewResponseDto());
        }
    }

    /**
     * 리뷰 점수 수정 API
     * [PATCH] /reviews/:reviewId/score
     * @param reviewId (수정할 리뷰 아이디 )
     * @return review(삭제된 리뷰)
     */
    @PatchMapping("/{reviewId}/score")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateScore(@PathVariable("reviewId") Long reviewId, @Valid @RequestBody ReviewRequestDto reviewRequestDto){
        Optional<Review> review = reviewService.getReviewById(reviewId);
        if(review.isEmpty()){
            return ResponseEntity.badRequest().body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_REVIEW));
        } else{
            reviewService.updateScore(review.get(),reviewRequestDto.getScore());
            return ResponseEntity.ok().body(review.get().toReviewResponseDto());
        }
    }

    /**
     * 리뷰 삭제 API
     * [DELETE] /reviews/:reviewId
     * @param reviewId
     * @return review(삭제된 리뷰 객체)
     */
    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteReview(@PathVariable("reviewId") Long reviewId){
        Optional<Review> review = reviewService.getReviewById(reviewId);
        if(review.isEmpty()){
            return ResponseEntity.badRequest().body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_REVIEW));
        } else{
            reviewService.deleteReview(review.get());
            return ResponseEntity.ok().body(review.get().toReviewResponseDto());
        }

    }
}
