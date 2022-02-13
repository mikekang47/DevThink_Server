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

    User user = User.builder().id(1L).point(10).build();
    Book book = Book.builder().id(1L).isbn("1234567891234").build();
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

        given(reviewRepository.save(any(Review.class))).willReturn(review);

        given(reviewRepository.findByIdAndDeletedIsFalse(1L)).willReturn(Optional.of(review));

        given(userRepository.save(user)).willReturn(user);
    }

    @Test
    void 리뷰작성시_유저_포인트_증가() {
        //given
        int prePoint = user.getPoint();
        ReviewRequestData reviewRequestData = ReviewRequestData.builder()
                .point(review.getPoint())
                .build();
        //when
        reviewService.createReview(user, book, reviewRequestData);
        //then
        assertThat(user.getPoint()).isEqualTo(prePoint + review.getPoint());
    }

    @Test
    void 리뷰삭제시_유저_포인트_감소() {
        //given
        int prePoint = user.getPoint();
        //when
        reviewService.deleteReview(review.getId());
        //then
        assertThat(user.getPoint()).isEqualTo(prePoint - review.getPoint());
    }

}