package com.devthink.devthink_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class CommentRequestDto {

    @NotBlank
    private final Long userId;

    private final Long postId;

    private final Long reviewId;

    @NotBlank
    @Size(min=1)
    private final String content;

}
