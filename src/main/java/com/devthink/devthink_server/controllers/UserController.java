// 1. 가입 -> POST /users
// 2. 사용자 정보 갱신 -> PUT/PATCH /users/{id}
// 3. 탈퇴 -> DELETE /users/{id}
package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.UserResultData;
import com.devthink.devthink_server.dto.UserRegistrationData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * valid한 사용자의 정보를 받아사어 사용자를 ㅅ앳
     * @param userRegistrationData
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserResultData create(@RequestBody @Valid UserRegistrationData userRegistrationData) {
        User user = userService.registerUser(userRegistrationData);

        return UserResultData.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole())
                .gitNickname(user.getGitNickname())
                .build();

    }


}
