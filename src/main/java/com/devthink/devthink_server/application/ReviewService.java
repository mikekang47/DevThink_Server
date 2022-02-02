package com.devthink.devthink_server.application;
import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.BookRequestData;
import com.devthink.devthink_server.dto.ReviewDetailResponseData;
import com.devthink.devthink_server.dto.ReviewRequestData;
import com.devthink.devthink_server.dto.ReviewResponseData;
import com.devthink.devthink_server.errors.ReviewNotFoundException;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.devthink.devthink_server.infra.BookRepository;
import com.devthink.devthink_server.infra.ReviewRepository;
import com.devthink.devthink_server.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    /**
     * 전달된 값으로 리뷰를 생성합니다.
     * @param reviewRequestData
     * @return 생성 된 리뷰 id
     */
    @Transactional
    public String createReview(ReviewRequestData reviewRequestData){
        User user = userRepository.findByIdAndDeletedIsFalse(reviewRequestData.getUserId())
                .orElseThrow(()-> new UserNotFoundException(reviewRequestData.getUserId()));
        Book book = getBookByIsbn(reviewRequestData.getBook());
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
     * 입력된 isbn 정보로 Book을 조회하며, 해당 책이 없는 경우 새로 생성하는 함수를 호출합니다.
     * @param bookRequestData (책에 대한 정보)
     * @return 조회 혹은 생성된 Book 객체
     */
    public Book getBookByIsbn(BookRequestData bookRequestData) {
        Optional<Book> book = bookRepository.getBookByIsbn(bookRequestData.getIsbn());
        if (book.isEmpty()) {
            return createBook(bookRequestData);
        } else {
            return book.get();
        }
    }

    /**
     * 입력된 책 정보의 새로운 Book 객체를 생성합니다.
     * @param bookRequestData (책에 대한 정보)
     * @return 생성된 Book 객체
     */
    @Transactional
    public Book createBook(BookRequestData bookRequestData) {
        Book book = Book.builder()
                .isbn(bookRequestData.getIsbn())
                .name(bookRequestData.getName())
                .writer(bookRequestData.getWriter())
                .imgUrl(bookRequestData.getImgUrl())
                .build();
        return bookRepository.save(book);
    }

    /**
     * 입력된 리뷰 식별자 (id) 값으로 리뷰 상세 정보를 가져옵니다.
     * @param id (리뷰 식별자)
     * @return 조회된 리뷰
     */
    public ReviewDetailResponseData getReviewDetailById(Long id) {
        Review review = getReviewById(id);
        return review.toReviewDetailResponseData();
    }

    /**
     * 전달된 리뷰의 내용을 수정합니다.
     * @param id (수정할 리뷰 식별자)
     * @param content (수정할 내용)
     * @return 수정된 리뷰
     */
    @Transactional
    public ReviewResponseData updateContent(Long id, String content){
        Review review = getReviewById(id);
        review.setContent(content);
        return review.toReviewResponseData();
    }

    /**
     * 전달된 리뷰의 별점을 수정합니다.
     * @param id (수정할 리뷰 식별자)
     * @param score (수정할 점수)
     * @return 수정된 리뷰
     */
    @Transactional
    public ReviewResponseData updateScore(Long id, BigDecimal score){
        Review review = getReviewById(id);
        review.setScore(score);
        return review.toReviewResponseData();
    }

    /**
     * 전달된 리뷰의 deleted 컬럼을 true로 변경하고, 해당 책의 리뷰수를 감소시키며, 평점을 다시 계산합니다.
     * @param id (삭제할 리뷰 식별자)
     * @return 수정된 리뷰
     */
    @Transactional
    public void deleteReview(long id){
        Review review = getReviewById(id);
        review.setDeleted(true);
        review.getBook().downReviewCnt();
        review.getBook().setScoreAvg(bookRepository.calcScoreAvg(review.getBook().getId()));
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

}
