package com.devthink.devthink_server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer isbn;

    @Builder.Default
    private Integer reviewCnt = 0;

    @Builder.Default
    private BigDecimal scoreAvg = new BigDecimal("0");

    @Builder.Default
    @OneToMany(mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();


    /**
     * 리뷰 추가 로직
     * 전체 점수 증가, 리뷰 수 증가, 점수 평균 계산
     * @param
     */
    public void addReview(Review review) {
        review.setBook(this);
        this.reviewCnt++;
        // 평점 계산
        BigDecimal sum = new BigDecimal("0");
        for (Review x : reviews){
            sum = sum.add(x.getScore());
            System.out.println(sum);
        }
        this.scoreAvg = sum.divide(new BigDecimal(reviewCnt.toString()));
    }

}
