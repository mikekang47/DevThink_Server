package com.devthink.devthink_server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;

@Getter
@ApiModel("사용자 로그인 정보")
public class SessionRequestData {
    @ApiParam(value = "가입된 사용자의 이메일", required = true, example = "test@email.com")
    private String email;

    @ApiParam(value = "가입된 사용자의 비밀번호", required = true, example = "test12345678")
    private String password;
}
