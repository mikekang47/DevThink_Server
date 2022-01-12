package com.devthink.devthink_server.dto;

import com.devthink.devthink_server.domain.Post;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class PostDto {

    private Long id;

    private Long user_id;

    private String title;

    private String content;

    private String status;

    private LocalDateTime create_at;

    private LocalDateTime update_at;

    public Post toEntity(){
        Post build = Post.builder()
                .id(id)
                .user_id(user_id)
                .title(title)
                .content(content)
                .status(status)
                .build();
        return build;
    }

    @Builder
    public PostDto(Long id, Long user_id, String title, String content, String status, LocalDateTime create_at, LocalDateTime update_at) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.create_at = create_at;
        this.update_at = update_at;
    }


}
