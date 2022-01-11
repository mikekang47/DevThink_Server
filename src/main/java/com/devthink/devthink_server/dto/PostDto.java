package com.devthink.devthink_server.dto;

import com.devthink.devthink_server.domain.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    private String title;

    private String content;

    private String status;

    private LocalDateTime create_at;

    private LocalDateTime update_at;

    public Post toEntity(){
        Post post = Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .status(status)
                .build();
        return post;
    }



}
