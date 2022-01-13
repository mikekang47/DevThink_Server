package com.devthink.devthink_server.domain;

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
}
