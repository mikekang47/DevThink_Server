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

    private UserProfileData userProfile;

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
