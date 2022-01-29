package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.application.BookService;
import com.devthink.devthink_server.application.ReviewService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReviewRequestData;
import com.devthink.devthink_server.dto.ReviewResponseData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
     * @param reviewRequestData (userId, bookIsbn, content, score)
     * @return 새로 생성된 리뷰 아이디
     */
    @PostMapping
    @ApiOperation(value = "리뷰 등록", notes = "전달된 정보에 따라 리뷰를 등록합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody ReviewRequestData reviewRequestData){
        User user = userService.getUser(reviewRequestData.getUserId());
        Book book = bookService.getBookByIsbn(reviewRequestData.getBook());
        String id = reviewService.createReview(user, book, reviewRequestData);
        return id;
    }


    /**
     * 주어진 id 의 리뷰를 상세 조회합니다.
     * [GET] /reviews/:id
     * @param id (조회할 리뷰 아이디)
     * @return ReviewResponseDto (조회한 리뷰 정보 )
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "리뷰 상세 조회", notes = "식별자 값의 리뷰를 상세 조회합니다.")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReviewResponseData detail(@PathVariable("id") @ApiParam(value="리뷰 식별자 값") Long id){
        Review review = reviewService.getReviewById(id);
        return review.toReviewResponseDto();
    }


    /**
     * 주어진 id 의 리뷰 내용을 수정합니다.
     * [PATCH] /reviews/:id/content
     * @param id (수정할 리뷰 아이디)
     */
    @PatchMapping("/{id}/content")
    @ApiOperation(value = "리뷰 내용 수정", notes = "식별자 값의 리뷰 내용을 전달된 내용으로 수정합니다.")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponseData updateContent(@PathVariable("id") @ApiParam(value="리뷰 식별자 값") Long id, @Valid @RequestBody ReviewRequestData reviewRequestData){
        Review review = reviewService.getReviewById(id);
        reviewService.updateContent(review, reviewRequestData.getContent());
        return review.toReviewResponseDto();
    }


    /**
     * 주어진 id 의 리뷰 점수를 수정합니다.
     * [PATCH] /reviews/:id/score
     * @param id (수정할 리뷰 아이디 )
     * @return ReviewResponseDto (삭제된 리뷰 )
     */
    @PatchMapping("/{id}/score")
    @ApiOperation(value = "리뷰 별점 수정", notes = "식별자 값의 리뷰 별점을 전달된 별점으로 수정합니다.")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponseData updateScore(@PathVariable("id") @ApiParam(value="리뷰 식별자 값") Long id, @Valid @RequestBody ReviewRequestData reviewRequestData){
        Review review = reviewService.getReviewById(id);
        reviewService.updateScore(review, reviewRequestData.getScore());
        return review.toReviewResponseDto();
    }


    /**
     * 주어진 id 의 리뷰를 삭제합니다.
     * [DELETE] /reviews/:id
     * @param id
     * @return review(삭제된 리뷰 정보 )
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "리뷰 삭제", notes = "식별자 값의 리뷰를 삭제합니다.")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponseData destroy(@PathVariable("id") @ApiParam(value="리뷰 식별자 값") Long id){
        Review review = reviewService.getReviewById(id);
        reviewService.deleteReview(review);
        return review.toReviewResponseDto();
    }

}
