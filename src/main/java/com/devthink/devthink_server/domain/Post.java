package com.devthink.devthink_server.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue
    Long id;

    Long user_id;

    Long category_id;

    String title;

    String content;

    String status;

    @Builder
    public Post(Long id, Long user_id, String title, String content, String status) {
        this.id = id;
        this.user_id =user_id;
        this.title = title;
        this.content = content;
        this.status = status;
    }

}
