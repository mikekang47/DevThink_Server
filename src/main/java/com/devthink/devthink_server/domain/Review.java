package com.devthink.devthink_server.domain;

import com.devthink.devthink_server.dto.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;

    @ManyToOne(fetch = LAZY)
    private Book book;

    private String content;

    private BigDecimal score;

    @OneToMany(mappedBy = "review")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Review(User user, Book book, String content, BigDecimal score) {
        this.user = user;
        this.book = book;
        this.content = content;
        this.score = score;
    }

    public void setBook(Book book){
        this.book = book;
        book.getReviews().add(this);
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setScore(BigDecimal score){
        this.score = score;
    }

    //TODO: comments 추가
    public ReviewResponseDto toReviewResponseDto(){
        return ReviewResponseDto.builder()
                .id(id)
                .userId(user.getId())
                .bookIsbn(book.getIsbn())
                .content(content)
                .score(score)
                .build();
    }

}
