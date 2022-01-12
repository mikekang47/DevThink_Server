package com.devthink.devthink_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class UserRegistrationData {
    @NotBlank
    private String email;

    @NotBlank
    @Size(max=8)
    private String nickname;

    @NotBlank
    private String role;

    private List<String> stack;

    private String blogAddr;

    private String gitNickname;
}
