package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReviewModificationData;
import com.devthink.devthink_server.dto.ReviewRequestData;
import com.devthink.devthink_server.infra.BookRepository;
import com.devthink.devthink_server.infra.ReviewRepository;
import com.devthink.devthink_server.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class ReviewServiceTest {

    private ReviewService reviewService;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    BookRepository bookRepository;

    @Mock
    UserRepository userRepository;

    User user = User.builder().id(1L).point(10).build();
    Book book = Book.builder().id(1L).isbn("1234567891234").reviewCnt(1).scoreAvg(BigDecimal.valueOf(3.0)).build();
    Review review = Review.builder()
            .id(1L)
            .user(user)
            .book(book)
            .title("제목")
            .content("내용")
            .score(BigDecimal.valueOf(4.5))
            .point(5)
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //@Mock이 붙은 객체를 생성, 초기화
        reviewService = new ReviewService(reviewRepository, bookRepository);
    }

    @Test
    void 리뷰생성성공() {
        //given
        int prePoint = user.getPoint();
        ReviewRequestData reviewRequestData = ReviewRequestData.builder()
                .point(review.getPoint())
                .score(review.getScore())
                .build();
        given(reviewRepository.save(any(Review.class))).willReturn(review);
        given(userRepository.save(any(User.class))).willReturn(user);
        given(bookRepository.save(any(Book.class))).willReturn(book);
        given(bookRepository.calcScoreAvg(book.getId())).willReturn(BigDecimal.valueOf(3.75));
        //when
        reviewService.createReview(user, book, reviewRequestData);
        //then
        assertThat(user.getPoint()).isEqualTo(prePoint + review.getPoint()); // 유저 포인트 증가
        assertThat(book.getReviewCnt()).isEqualTo(2);   // 책 리뷰 수 증가
        assertThat(book.getScoreAvg()).isEqualTo(BigDecimal.valueOf(3.75)); // 책 평점 변화
    }

    @Test
    void 리뷰수정성공() {
        //given
        ReviewModificationData reviewModificationData = ReviewModificationData.builder().title("수정제목").content("수정내용").score(BigDecimal.valueOf(4.5)).build();
        Review modifiedReview = Review.builder()
                .id(1L)
                .user(user)
                .book(book)
                .title(reviewModificationData.getTitle())
                .content(reviewModificationData.getContent())
                .score(reviewModificationData.getScore())
                .point(5)
                .build();
        given(reviewRepository.findByIdAndDeletedIsFalse(any(Long.class))).willReturn(Optional.of(review));
        given(bookRepository.calcScoreAvg(book.getId())).willReturn(reviewModificationData.getScore());
        given(bookRepository.save(any(Book.class))).willReturn(book);
        given(reviewRepository.save(any(Review.class))).willReturn(modifiedReview);
        BigDecimal preScoreAvg = book.getScoreAvg();
        //when
        reviewService.update(1L, reviewModificationData);
        //then
        assertThat(review.getTitle()).isEqualTo(reviewModificationData.getTitle()); // title 변경
        assertThat(review.getContent()).isEqualTo(reviewModificationData.getContent()); // content 변경
        assertThat(review.getScore()).isEqualTo(reviewModificationData.getScore()); // score 변경
        assertThat(book.getScoreAvg()).isEqualTo(reviewModificationData.getScore()); // 책 평점 계산
    }

    @Test
    void 리뷰삭제성공() {
        //given
        int prePoint = user.getPoint();
        int preReviewCnt = book.getReviewCnt();
        given(reviewRepository.findByIdAndDeletedIsFalse(any(Long.class))).willReturn(Optional.of(review));
        given(bookRepository.calcScoreAvg(book.getId())).willReturn(BigDecimal.valueOf(0));
        //when
        reviewService.deleteReview(review.getId());
        //then
        assertThat(review.getDeleted()).isEqualTo(true); // 리뷰 deleted == false
        assertThat(user.getPoint()).isEqualTo(prePoint - review.getPoint()); // 유저 포인트 감소
        assertThat(book.getScoreAvg()).isEqualTo(BigDecimal.valueOf(0)); // 책 평점 계산
        assertThat(book.getReviewCnt()).isEqualTo(preReviewCnt - 1); // 책 리뷰 수 감소
    }

}