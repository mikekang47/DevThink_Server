package com.devthink.devthink_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    @NotNull
    private final Long commentId;

    @NotBlank
    private final String userNickname;

    @NotBlank
    private final String userRole;

    private final String userImageUrl;

    @NotBlank
    private final String content;

}
