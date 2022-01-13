package com.devthink.devthink_server.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity //게시판 board에 대한 entity
@Getter //getter 메서드 자동 추가
@NoArgsConstructor  //기본생성자 자동추가
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private Long category_id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 10, nullable = false)
    private String status;

    @Builder
    public Posts(Long user_id, Long category_id, String title, String content, String status) {
        super();
        this.user_id = user_id;
        this.category_id = category_id;
        this.title = title;
        this.content = content;
        this.status = status;
    }
}
