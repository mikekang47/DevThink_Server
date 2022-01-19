package com.devthink.devthink_server.dto;

import com.devthink.devthink_server.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;


@Getter
@Builder
@AllArgsConstructor
public class PostResultData extends BaseTimeEntity {
    private Long id;

    private Long user_id;

    private Long category_id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Integer hit;

    private String status;
}
