package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReviewDetailResponseData;
import com.devthink.devthink_server.dto.ReviewModificationData;
import com.devthink.devthink_server.dto.ReviewRequestData;
import com.devthink.devthink_server.dto.ReviewResponseData;
import com.devthink.devthink_server.errors.AlreadyReviewedException;
import com.devthink.devthink_server.errors.ReviewNotFoundException;
import com.devthink.devthink_server.infra.BookRepository;
import com.devthink.devthink_server.infra.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    /**
     * 전달된 값으로 리뷰를 생성하며, 유저에게 포인트가 적립됩니다.
     *
     * @param user, book, reviewRequestData
     * @return 생성 된 리뷰 객체
     */
    @Transactional
    public Review createReview(User user, Book book, ReviewRequestData reviewRequestData) {
        if (reviewRepository.existsByBookIdAndUserIdAndDeletedIsFalse(book.getId(), user.getId())) {
            /*
            주어진 책에 대해 주어진 사용자가 이미리뷰를 작성했는지 확인합니다.
            삭제한 경우는 작성하지 않은 것으로 간주합니다.
            */
            throw new AlreadyReviewedException(book.getId());
        }
        Review review = reviewRepository.save(
                Review.builder()
                        .user(user)
                        .book(book)
                        .title(reviewRequestData.getTitle())
                        .content(reviewRequestData.getContent())
                        .score(reviewRequestData.getScore())
                        .point(reviewRequestData.getPoint())
                        .build()
        );
        // 책에 리뷰를 추가하고 평점을 다시 계산합니다.
        review.getBook().addReview(review);
        review.getBook().setScoreAvg(reviewRepository.calcScoreAvg(book.getId()));
        user.upPoint(reviewRequestData.getPoint()); // 유저에게 포인트를 적립합니다.
        return review;
    }

    /**
     * 입력된 리뷰 식별자 (id) 값으로 리뷰 상세 정보를 가져옵니다.
     *
     * @param id (리뷰 식별자)
     * @return 조회된 리뷰
     */
    public ReviewDetailResponseData getReviewDetailById(Long id) {
        Review review = getReviewById(id);
        return review.toReviewDetailResponseData();
    }

    /**
     * 전달된 리뷰의 정보를 수정합니다.
     *
     * @param id      (수정할 리뷰 식별자)
     * @param reviewModificationData (수정할 내용을 담은 객체)
     * @return 수정된 리뷰
     */
    @Transactional
    public ReviewResponseData update(Long id, ReviewModificationData reviewModificationData) {
        Review review = getReviewById(id);
        review.update(reviewModificationData);
        review.getBook().setScoreAvg(reviewRepository.calcScoreAvg(review.getBook().getId())); // 평점을 다시 계산합니다.
        return review.toReviewResponseData();
    }

    /**
     * 전달된 리뷰의 deleted 컬럼을 true로 변경하고, 해당 책의 리뷰수를 감소시키며, 평점을 다시 계산합니다.
     * 사용자의 포인트를 회수합니다.
     *
     * @param id (삭제할 리뷰 식별자)
     */
    @Transactional
    public void deleteReview(long id) {
        Review review = getReviewById(id);
        review.setDeleted(true);
        review.getBook().downReviewCnt(); // 책의 리뷰수를 감소시킵니다.
        review.getBook().setScoreAvg(reviewRepository.calcScoreAvg(review.getBook().getId()));    // 책의 평점을 다시 계산합니다.
        review.getUser().downPoint(review.getPoint()); // 사용자의 포인트를 회수합니다.
    }

    /**
     * 입력된 리뷰 식별자 (id) 값으로 리뷰를 가져옵니다.
     *
     * @param id (리뷰 식별자)
     * @return 조회된 리뷰
     */
    public Review getReviewById(Long id) {
        return reviewRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

}
