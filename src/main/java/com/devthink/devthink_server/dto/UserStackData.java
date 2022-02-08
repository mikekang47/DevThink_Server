package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@ApiModel("사용자 스택 정보")
public class UserStackData {
    @NotNull
    @ApiModelProperty(value = "사용자 식별자", example = "20")
    private Long userId;

    @ApiModelProperty(value = "스택 식별자", example = "20")
    private Long stackId;

}
