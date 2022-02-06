package com.devthink.devthink_server.dto;

import com.devthink.devthink_server.domain.BaseTimeEntity;
import com.github.dozermapper.core.Mapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Builder
@Getter
@AllArgsConstructor
@ApiModel(value = "사용자 가입 정보")
public class UserRegistrationData extends BaseTimeEntity {
    @NotBlank(message = "이메일 주소를 입력하세요.")
    @Email
    @Mapping("email")
    @ApiParam(value = "사용자 이메일", required = true, example = "test@email.com")
    private String email;

    @NotBlank
    @Mapping("password")
    @Size(min=8)
    @ApiParam(value = "사용자 비밀번호", required = true, example = "test12345678")
    private String password;

    @NotBlank
    @Mapping("phoneNum")
    @Size(max=13)
    @ApiParam(value = "사용자 전화번호", required = true, example = "010-1234-1234")
    private String phoneNum;

    @NotBlank
    @Mapping("name")
    @ApiParam(value = "사용자 이름", required = true, example = "김싱크")
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(max=8)
    @Mapping("nickname")
    @ApiParam(value = "사용자 닉네임", required = true, example = "싱크싱크")
    private String nickname;

    @NotBlank(message = "직무를 선택해주세요.")
    @Mapping("role")
    @ApiParam(value = "사용자 수준", required = true, example = "주니어")
    private String role;

//    @Mapping("stack")
//    @ApiParam(value = "사용자가 사용하는 기술", example = "C++, Java, Spring")
//    private List<Long> stack;

    @Mapping("blogAddr")
    @ApiParam(value = "사용자의 기술 블로그 주소", example = "mirrorofcode.tistory.com")
    private String blogAddr;

    @Mapping("gitNickname")
    @ApiParam(value = "사용자의 github 닉네임", example = "mikekang47")
    private String gitNickname;

    @Mapping("point")
    @ApiParam(value = "사용자의 점수", example = "128")
    private Integer point;

    public UserRegistrationData() {
//        this.stack = new ArrayList<>();
        this.blogAddr = "";
        this.gitNickname = "";
        this.point = 0;
    }
}
