package com.devthink.devthink_server.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @Mapping("user_id")
    private Long user_id;

    @Mapping("category_id")
    private Long category_id;

    @Mapping("title")
    private String title;

    @Mapping("content")
    private String content;

    @Mapping("status")
    private String status;


}
