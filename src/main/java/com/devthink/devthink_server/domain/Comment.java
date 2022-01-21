package com.devthink.devthink_server.domain;

import com.devthink.devthink_server.dto.CommentResponseDto;
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
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = LAZY)
    User user;

    @ManyToOne(fetch = LAZY)
    Post post;

    @ManyToOne(fetch = LAZY)
    Review review;

    String content;

    String status;

    public CommentResponseDto toCommentResponseDto() {
        return CommentResponseDto.builder()
                .user_nickname(user.getNickname())
                .user_role(user.getRole())
                .content(content)
                .build();
    }

    public void setContent(String content) {
        this.content = content;
    }

}
