// 1. 가입 -> POST /users
// 2. 사용자 정보 갱신 -> PUT/PATCH /users/{id}
// 3. 탈퇴 -> DELETE /users/{id}
package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserData create(@RequestBody UserData userData) {
        User user = userService.registerUser(userData);

        return UserData.builder()
                .email(user.getEmail())
                .build();
    }


}
