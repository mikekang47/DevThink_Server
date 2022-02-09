package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ApiModel("댓글 조회 정보")
public class ReplyResponseData {

    @ApiModelProperty(value = "댓글 식별자", example = "1")
    @NotNull
    private final Long replyId;

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

    @ApiModelProperty(value = "댓글 생성 시각", example = "")
    @CreatedDate
    private LocalDateTime createAt;

    @ApiModelProperty(value = "댓글 수정 시각", example = "")
    @LastModifiedDate
    private LocalDateTime updateAt;

}
