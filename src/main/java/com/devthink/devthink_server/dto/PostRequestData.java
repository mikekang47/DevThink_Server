package com.devthink.devthink_server.dto;

import com.github.dozermapper.core.Mapping;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
public class PostRequestData {

    @ApiModelProperty(value = "카테고리 아이디", example = "1")
    @ApiParam(value = "카테고리 아이디", example = "1")
    private Long categoryId;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min=1)
    @ApiModelProperty(value = "제목", example = "example")
    @ApiParam(value = "제목", example = "example")
    @Mapping("title")
    private String title;

    @ApiModelProperty(value = "프로젝트 부제목", example = "example")
    @ApiParam(value = "프로젝트 부제목", example = "example")
    private String subTitle;

    @NotBlank(message = "글의 내용을 입력해주세요.")
    @Size(min=1)
    @ApiModelProperty(value = "내용", example = "example1")
    @ApiParam(value = "내용", example = "example1")
    @Mapping("content")
    private String content;

    @ApiParam(value = "이미지 url", example = "example1.com")
    private String imageUrl;

    public PostRequestData() {
        this.imageUrl = "";
        this.subTitle = "";
    }

}
