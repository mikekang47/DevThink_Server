package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Builder
@ApiModel("사용자 스택 정보")
public class UserStackData {
    @NotNull
    @ApiModelProperty(value = "사용자 토큰", example = "1231dafaber324d1")
    private String accessToken;

    @ApiModelProperty(value = "스택 식별자", example = "20")
    private Long stackId;

}
