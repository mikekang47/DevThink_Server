package com.devthink.devthink_server.application;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReviewRequestDto;
import com.devthink.devthink_server.infra.JpaReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final JpaReviewRepository jpaReviewRepository;

    // 리뷰 등록
    public String createReview(User user, Book book, ReviewRequestDto reviewRequestDto){
        Review review = jpaReviewRepository.save(
            Review.builder()
                    .user(user)
                    .book(book)
                    .content(reviewRequestDto.getContent())
                    .score(reviewRequestDto.getScore())
                    .build()
        );
        return review.getId().toString();
    }
}
