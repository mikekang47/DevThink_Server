package com.devthink.devthink_server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = LAZY)
    User user;

    @ManyToOne
    Comment comment;

    String content;

    String status;
}
