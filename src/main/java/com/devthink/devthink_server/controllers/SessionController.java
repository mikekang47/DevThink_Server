package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.AuthenticationService;
import com.devthink.devthink_server.dto.SessionRequestData;
import com.devthink.devthink_server.dto.SessionResponseData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/session")
@RestController
public class SessionController {

    private AuthenticationService authenticationService;

    public SessionController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "사용자 로그인", notes = "사용자의 세션 데이터를 입력받아 로그인을 진행합니다.")
    @ApiImplicitParam(required = true, name = "사용자 로그인 데이터", example = "abc@email.com, 123567890")
    public SessionResponseData login(@RequestBody SessionRequestData sessionRequestData) {
        String email = sessionRequestData.getEmail();
        String password = sessionRequestData.getPassword();

        String accessToken = authenticationService.login(email, password);

        return SessionResponseData.builder()
                .accessToken(accessToken)
                .build();

    }

}
