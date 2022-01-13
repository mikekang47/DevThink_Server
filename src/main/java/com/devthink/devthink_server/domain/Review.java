package com.devthink.devthink_server.domain;

import com.devthink.devthink_server.dto.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private Float score;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Review(User user, Book book, String content, Float score) {
        this.user = user;
        this.book = book;
        this.content = content;
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
