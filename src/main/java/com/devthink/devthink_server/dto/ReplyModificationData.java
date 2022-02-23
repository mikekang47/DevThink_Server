package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("대댓글 수정 정보")
public class ReplyModificationData {

    @ApiModelProperty(value = "대댓글 내용", required = true, example = "안녕하세요")
    @NotBlank(message = "Reply content cannot be empty")
    @Size(min=1)
    private String content;

}
