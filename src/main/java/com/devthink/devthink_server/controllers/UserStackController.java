package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.application.UserStackService;
import com.devthink.devthink_server.domain.UserStack;
import com.devthink.devthink_server.dto.StackData;
import com.devthink.devthink_server.dto.UserStackData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/stacks")
public class UserStackController {
    private final UserStackService userStackService;

    @GetMapping("/{userId}")
    public StackData detail(@PathVariable Long userId) {
        UserStack userStack = userStackService.getUserStack(userId);
        return getUserStackData(userStack);
    }

    @PostMapping("/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    public StackData createUserStack(@RequestBody @Valid UserStackData userStackData) {
        UserStack userStack = userStackService.create(userStackData);
        return getUserStackData(userStack);
    }
    
    private StackData getUserStackData(UserStack userStack) {
        return StackData.builder()
                .name(userStack.getStack().getName())
                .build();

    }

}
