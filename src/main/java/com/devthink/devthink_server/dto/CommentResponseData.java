package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("댓글 조회 정보")
public class CommentResponseData {

    @ApiModelProperty(value = "댓글 식별자", example = "1")
    @NotNull
    private final Long commentId;

    @ApiModelProperty(value = "사용자 식별자", example = "1")
    @NotNull
    private final Long userId;

    @ApiModelProperty(value = "사용자 닉네임", example = "싱크싱크")
    @NotBlank
    private final String userNickname;

    @ApiModelProperty(value = "사용자 프로필 이미지 주소", example = "https://picsum.photos/200")
    private final String userImageUrl;

    @ApiModelProperty(value = "댓글 내용", example = "안녕하세요")
    @NotBlank
    private final String content;

}
