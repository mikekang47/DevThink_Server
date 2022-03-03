package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.application.BookService;
import com.devthink.devthink_server.application.ReviewService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReviewDetailResponseData;
import com.devthink.devthink_server.dto.ReviewModificationData;
import com.devthink.devthink_server.dto.ReviewRequestData;
import com.devthink.devthink_server.dto.ReviewResponseData;
import com.devthink.devthink_server.errors.PointNotValidException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final BookService bookService;

    /**
     * 입력한 valid한 리뷰 정보를 받아 리뷰를 생성합니다.
     * [POST] /reviews
     *
     * @param reviewRequestData (userId, bookIsbn, content, score)
     * @return 새로 생성된 리뷰 아이디
     */
    @PostMapping
    @ApiOperation(value = "리뷰 등록", notes = "전달된 정보에 따라 리뷰를 등록합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponseData create(@Valid @RequestBody ReviewRequestData reviewRequestData) {
        User user = userService.getUser(reviewRequestData.getUserId());
        Book book = bookService.getOrCreateBook(reviewRequestData.getBook());
        int point = reviewRequestData.getPoint();
        if (point != 0 && point != 5 && point != 7) { // point 값이 0, 5, 7 중 하나여야 합니다.
            throw new PointNotValidException();
        }
        Review review = reviewService.createReview(user, book, reviewRequestData);
        return review.toReviewResponseData();
    }


    /**
     * 주어진 id 의 리뷰를 상세 조회합니다.
     * [GET] /reviews/:id
     *
     * @param id (조회할 리뷰 아이디)
     * @return ReviewDetailResponseData ( 사용자 프로필, 리뷰, 댓글 )
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "리뷰 상세 조회", notes = "식별자 값의 리뷰를 상세 조회합니다.")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReviewDetailResponseData detail(@PathVariable("id") @ApiParam(value = "리뷰 식별자 값") Long id) {
        return reviewService.getReviewDetailById(id);
    }

    /**
     * 주어진 id 의 리뷰를 수정합니다.
     * [PUT] /reviews/:id
     *
     * @param id (수정할 리뷰 아이디 )
     * @return ReviewResponseDto (삭제된 리뷰 )
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "리뷰 수정", notes = "식별자 값의 리뷰를 수정합니다.")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponseData update(@PathVariable("id") @ApiParam(value = "리뷰 식별자 값") Long id, @Valid @RequestBody ReviewModificationData reviewModificationData) {
        return reviewService.update(id, reviewModificationData);
    }


    /**
     * 주어진 id 의 리뷰를 삭제합니다.
     * [DELETE] /reviews/:id
     *
     * @param id
     * @return review(삭제된 리뷰 정보)
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "리뷰 삭제", notes = "식별자 값의 리뷰를 삭제합니다.")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public void destroy(@PathVariable("id") @ApiParam(value = "리뷰 식별자 값") Long id) {
        reviewService.deleteReview(id);
    }

}
