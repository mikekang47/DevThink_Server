package com.devthink.devthink_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min=8,max=15)
    private String password;

    @NotBlank
    @Size(max=8)
    private String nickname;

    @NotBlank
    private String role;

    private String stack;

    private String blogAddr;

    private String gitNickname;

    private Integer point;
}
