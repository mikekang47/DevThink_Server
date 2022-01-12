package com.devthink.devthink_server.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationData {
    @NotBlank
    @Mapping("email")
    private String email;

    @NotBlank
    @Size(max=8)
    @Mapping("nickname")
    private String nickname;

    @NotBlank
    @Mapping("role")
    private String role;

    @Mapping("stack")
    private List<String> stack;

    @Mapping("blogAddr")
    private String blogAddr;

    @Mapping("gitNickname")
    private String gitNickname;
}
