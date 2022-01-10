package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    User registerUser(@RequestBody User user) {
        return userRepository.save(user);
    }


}
