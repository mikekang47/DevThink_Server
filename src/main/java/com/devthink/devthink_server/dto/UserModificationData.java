package com.devthink.devthink_server.dto;

import com.github.dozermapper.core.Mapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
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
@ApiModel("사용자 수정 정보")
public class UserModificationData {
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(max=8)
    @Mapping("nickname")
    @ApiParam(value = "사용자 닉네임", example = "싱크싱크")
    private String nickname;

    @NotBlank(message = "비멀번호를 입력해주세요.")
    @Size(min=8)
    @Mapping("password")
    @ApiParam(value = "사용자 비밀번호", required = true, example = "test12345678")
    private String password;

    @NotBlank(message = "직무를 선택해주세요.")
    @Mapping("role")
    @ApiParam(value = "사용자 수준", required = true, example = "주니어")
    private String role;

    @Mapping("stack")
    @ApiParam(value = "사용자가 사용하는 기술", example = "C++, Java, Spring")
    private List<String> stack;

    @Mapping("blogAddr")
    @ApiParam(value = "사용자의 기술 블로그 주소", example = "mirrorofcode.tistory.com")
    private String blogAddr;

    @Mapping("gitNickname")
    @ApiParam(value = "사용자의 github 닉네임", example = "mikekang47")
    private String gitNickname;

    private Integer point;

    private boolean deleted;

}
