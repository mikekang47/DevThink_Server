package com.devthink.devthink_server.dto;

import com.devthink.devthink_server.domain.posts.Posts;
import lombok.Builder;

public class PostsResponseDto {
    private Long id;
    private Long user_id;
    private Long category_id;
    private String title;
    private String content;
    private String status;

    @Builder
    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.user_id = entity.getUser_id();
        this.category_id = entity.getCategory_id();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.status = entity.getStatus();
    }

}
