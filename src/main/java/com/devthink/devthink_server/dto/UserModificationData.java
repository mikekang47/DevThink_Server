package com.devthink.devthink_server.dto;

import com.github.dozermapper.core.Mapping;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserModificationData {
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(max=8)
    @Mapping("nickname")
    @ApiModelProperty(value = "사용자 닉네임", example = "싱크싱크")
    private String nickname;

    @NotBlank(message = "비멀번호를 입력해주세요.")
    @Size(min=8)
    @Mapping("password")
    @ApiModelProperty(value = "사용자 비밀번호", example = "test12345678")
    private String password;

    @NotBlank(message = "직무를 선택해주세요.")
    @Mapping("role")
    @ApiModelProperty(value = "사용자 수준", example = "주니어")
    private String role;

    @Mapping("stack")
    @ApiModelProperty(value = "사용자가 사용하는 기술", example = "C++, Java, Spring")
    private List<String> stack;

    @Mapping("blogAddr")
    @ApiModelProperty(value = "사용자의 기술 블로그 주소", example = "mirrorofcode.tistory.com")
    private String blogAddr;

    @Mapping("gitNickname")
    @ApiModelProperty(value = "사용자의 github 닉네임", example = "mikekang47")
    private String gitNickname;

    @ApiModelProperty(value = "사용자의 점수", example = "128")
    private Integer point;

    @ApiModelProperty(value = "사용자 삭제 여부", example = "true")
    private boolean deleted;
    
}
