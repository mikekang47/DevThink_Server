package com.devthink.devthink_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UserStackData {
    @NotNull
    private Long userId;

    @NotNull
    private Long stackId;

}
