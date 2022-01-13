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
    public String createReview(@RequestBody ReviewRequestDto reviewRequestDto){
        //TODO: userId 로 User 가져오기
        User user = new User();
        user.setId(reviewRequestDto.getUserId());
        // Isbn 으로 Book 가져오기
        Book book = bookService.getBookByIsbn(reviewRequestDto.getBookIsbn());
        String reviewId = reviewService.createReview(user, book, reviewRequestDto);
        return reviewId;
    }


    /**
     * 리뷰 상세 조회 API
     * [GET] /reviews/:reviewId
     * @param reviewId (조회 할 리뷰 아이디)
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

}
