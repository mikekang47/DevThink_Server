package com.devthink.devthink_server.domain;

import com.devthink.devthink_server.dto.ReviewDetailResponseData;
import com.devthink.devthink_server.dto.ReviewResponseData;
import lombok.*;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private String title;

    private String content;

    private BigDecimal score;

    private Integer point;

    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "review")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    private Integer heartCnt = 0;

    public void setBook(Book book) {
        this.book = book;
        book.getReviews().add(this);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void updateHeart(int heartCnt) {
        this.heartCnt = heartCnt;
    }

    public ReviewResponseData toReviewResponseData() {
        return ReviewResponseData.builder()
                .id(id)
                .userId(user.getId())
                .bookIsbn(book.getIsbn())
                .content(content)
                .score(score)
                .createAt(getCreateAt())
                .updateAt(getUpdateAt())
                .build();
    }

    /**
     * 댓글 리스트가 추가 된 리뷰 상세 정보로 변환합니다.
     * @return 변환 된 CommentDetailResponseData 객체
     */
    public ReviewDetailResponseData toReviewDetailResponseData() {
        return ReviewDetailResponseData.builder()
                .userProfile(user.toUserProfileData())
                .review(toReviewResponseData())
                .comments(comments.stream().map(Comment::toCommentDetailResponseData).collect(Collectors.toList()))
                .build();
    }

}
