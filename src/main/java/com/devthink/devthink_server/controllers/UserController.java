package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.dto.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserData registerUser() {
        return null;
    }


}
