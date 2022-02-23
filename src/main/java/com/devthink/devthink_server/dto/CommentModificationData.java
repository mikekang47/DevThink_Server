package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("댓글 수정 정보")
public class CommentModificationData {

    @ApiModelProperty(value = "댓글 내용", required = true, example = "안녕하세요")
    @NotBlank(message = "Comment content cannot be empty")
    @Size(min=1)
    private String content;

}
