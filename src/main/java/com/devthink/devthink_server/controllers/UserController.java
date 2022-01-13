// 1. 가입 -> POST /users
// 2. 사용자 정보 갱신 -> PUT/PATCH /users/{id}
// 3. 탈퇴 -> DELETE /users/{id}
package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.UserResultData;
import com.devthink.devthink_server.dto.UserRegistrationData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/emailCheck/{userEmail}")
    public ResponseEntity<Boolean> checkEmail(@PathVariable String userEmail) {
        return ResponseEntity.ok(userService.isDuplicateEmail(userEmail));
    }

    @GetMapping("/nicknameCheck/{{userNickname}")
    public ResponseEntity<Boolean> checkNickname(@PathVariable String userNickname) {
        return ResponseEntity.ok(userService.isDuplicateNickname(userNickname));
    }


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
