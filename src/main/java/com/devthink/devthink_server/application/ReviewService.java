package com.devthink.devthink_server.application;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReviewDetailResponseData;
import com.devthink.devthink_server.dto.ReviewRequestData;
import com.devthink.devthink_server.dto.ReviewResponseData;
import com.devthink.devthink_server.errors.ReviewNotFoundException;
import com.devthink.devthink_server.infra.BookRepository;
import com.devthink.devthink_server.infra.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    /**
     * 전달된 값으로 리뷰를 생성합니다.
     * @param user, book, reviewRequestDto
     * @return 생성 된 리뷰 id
     */
    @Transactional
    public String createReview(User user, Book book, ReviewRequestData reviewRequestData){
        Review review = reviewRepository.save(
            Review.builder()
                    .user(user)
                    .book(book)
                    .content(reviewRequestData.getContent())
                    .score(reviewRequestData.getScore())
                    .build()
        );
        review.getBook().addReview(review);
        review.getBook().setScoreAvg(bookRepository.calcScoreAvg(book.getId()));
        return review.getId().toString();
    }

    /**
     * 입력된 리뷰 식별자 (id) 값으로 리뷰를 가져옵니다.
     * @param id (리뷰 식별자)
     * @return 조회된 리뷰
     */
    public Review getReviewById(Long id) {
        return reviewRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

    /**
     * 입력된 리뷰 식별자 (id) 값으로 리뷰를 가져옵니다.
     * @param id (리뷰 식별자)
     * @return 조회된 리뷰
     */
    public ReviewDetailResponseData getReviewDetailById(Long id) {
        Review review = getReviewById(id);
        //TODO: Comment 불러오기
        return ReviewDetailResponseData.builder()
                .review(review.toReviewResponseDto())
                .userProfile(review.getUser().toUserProfileData())
                .build();
    }

    /**
     * 전달된 리뷰의 내용을 수정합니다.
     * @param review(수정할 리뷰), content(수정할 내용)
     */
    @Transactional
    public void updateContent(Review review, String content){
        review.setContent(content);
    }

    /**
     * 전달된 리뷰의 점수를 수정합니다.
     * @param review(수정할 리뷰), score(수정할 점수)
     */
    @Transactional
    public void updateScore(Review review, BigDecimal score){
        review.setScore(score);
    }

    /**
     * 전달된 리뷰의 deleted 컬럼을 true로 변경하고, 해당 책의 리뷰수를 감소시키며, 평점을 다시 계산합니다.
     * @param review (삭제할 리뷰)
     */
    @Transactional
    public void deleteReview(Review review){
        review.setDeleted(true);
        review.getBook().downReviewCnt();
        review.getBook().setScoreAvg(bookRepository.calcScoreAvg(review.getBook().getId()));
    }

}
