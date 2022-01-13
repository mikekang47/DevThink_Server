package com.devthink.devthink_server.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModificationData {
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(max=8)
    @Mapping("nickname")
    private String nickname;

    @NotBlank(message = "직무를 선택해주세요.")
    @Mapping("role")
    private String role;

    @Mapping("stack")
    private List<String> stack;

    @Mapping("blogAddr")
    private String blogAddr;

    @Mapping("gitNickname")
    private String gitNickname;

    @Mapping("point")
    private Integer point;

    @Mapping("status")
    private String status;

}
