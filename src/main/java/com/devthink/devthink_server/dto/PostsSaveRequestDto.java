package com.devthink.devthink_server.dto;

import com.devthink.devthink_server.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostsSaveRequestDto {
    private Long user_id;
    private Long category_id;
    private String title;
    private String content;
    private String status;

    @Builder
    public PostsSaveRequestDto(Long user_id, Long category_id, String title, String content, String status){
        this.user_id = user_id;
        this.category_id = category_id;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    //dto에서 필요한 부분을 eneity화 시킴
    public Posts toEntity(){
        return Posts.builder()
                .user_id(user_id)
                .category_id(category_id)
                .title(title)
                .content(content)
                .status(status)
                .build();
    }
}
