package com.devthink.devthink_server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer isbn;

    private Float scoreTotal;

    private Integer reviewCnt;

    private Float scoreAvg;

    @OneToMany
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Book(Integer isbn) {
        this.isbn = isbn;
    }


}
