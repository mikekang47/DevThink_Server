package com.devthink.devthink_server.application;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReviewRequestDto;
import com.devthink.devthink_server.dto.ReviewResponseDto;
import com.devthink.devthink_server.infra.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 등록
    public String createReview(User user, Book book, ReviewRequestDto reviewRequestDto){
        Review review = reviewRepository.save(
            Review.builder()
                    .user(user)
                    .book(book)
                    .content(reviewRequestDto.getContent())
                    .score(reviewRequestDto.getScore())
                    .build()
        );
        return review.getId().toString();
    }

    //리뷰 조회
    public ReviewResponseDto getReview(Long reviewId) {
        Review review = reviewRepository.getById(reviewId);
        return ReviewResponseDto.builder()
                .id(reviewId)
                .bookIsbn(review.getBook().getIsbn())
                .content(review.getContent())
                .score(review.getScore())
                .userId(review.getUser().getId())
                .build();
    }
}
