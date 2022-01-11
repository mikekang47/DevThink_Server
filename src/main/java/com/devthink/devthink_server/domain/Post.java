package com.devthink.devthink_server.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = LAZY)
    User user;

    //Category category;

    String title;

    String content;

    String status;

}