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
    private User user;

    @ManyToOne(fetch = LAZY)
    private Book book;

    private String content;

    private BigDecimal score;

    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "review")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

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

    public ReviewDetailResponseData toReviewDetailResponseData() {
        return ReviewDetailResponseData.builder()
                .userProfile(user.toUserProfileData())
                .review(toReviewResponseData())
                .comments(comments.stream().map(Comment::toCommentResponseDto).collect(Collectors.toList()))
                .build();
    }

}
