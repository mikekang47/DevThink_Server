package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //@Mock이 붙은 객체를 생성, 초기화
        reviewService = new ReviewService(reviewRepository, bookRepository);
    }

    @Test
    void 리뷰작성시_유저_포인트_증가() {
        //given
        User user = User.builder().id(1L).point(0).build();
        Book book = Book.builder().id(1L).isbn("1234567891234").build();
        given(reviewRepository.save(any(Review.class))).will(invocation -> {
            Review review = Review.builder()
                    .id(1L)
                    .user(user)
                    .book(book)
                    .title("제목")
                    .content("내용")
                    .score(BigDecimal.valueOf(4.5))
                    .build();
            return review;
        });
        ReviewRequestData reviewRequestData = ReviewRequestData.builder()
                .userId(1L)
                .score(BigDecimal.valueOf(4.5))
                .content("반갑습니당")
                .point(5)
                .build();
        //when
        reviewService.createReview(user, book, reviewRequestData);
        //then
        assertThat(user.getPoint()).isEqualTo(5);
    }

    @Test
    void 리뷰삭제시_유저_포인트_감소() {
        //given
        User user = User.builder().id(1L).point(10).build();
        Book book = Book.builder().id(1L).isbn("1234567891234").build();
        given(reviewRepository.findByIdAndDeletedIsFalse(1L)).will(invocation -> {
            Review review = Review.builder()
                    .id(1L)
                    .user(user)
                    .book(book)
                    .title("제목")
                    .content("내용")
                    .point(7)
                    .score(BigDecimal.valueOf(4.5))
                    .build();
            return Optional.of(review);
        });
        given(userRepository.save(user)).will(invocation -> {
            return User.builder()
                    .id(user.getId())
                    .point(user.getPoint())
                    .build();
        });
        //when
        reviewService.deleteReview(1L);
        //then
        assertThat(user.getPoint()).isEqualTo(3);
    }

}