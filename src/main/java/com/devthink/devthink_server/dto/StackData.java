package com.devthink.devthink_server.dto;

import com.github.dozermapper.core.Mapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@ApiModel("스택 정보")
public class StackData {

    @Mapping("name")
    @NotNull
    @NotBlank(message = "이름이 공백일 수 없습니다.")
    @ApiModelProperty(value = "스택 이름", example = "C/C++")
    private String name;

}
