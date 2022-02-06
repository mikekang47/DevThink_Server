package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.application.UserStackService;
import com.devthink.devthink_server.domain.UserStack;
import com.devthink.devthink_server.dto.StackData;
import com.devthink.devthink_server.dto.UserStackData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/stacks")
public class UserStackController {
    private final UserStackService userStackService;

    @Getter("/{id}")
    public StackData detail(@PathVariable Long userId) {
        UserStack userStack = userStackService.getUserStack(userId);
    }

    @PostMapping("/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    public StackData createUserStack(@RequestBody @Valid UserStackData userStackData) {
        UserStack userStack = userStackService.create(userStackData);
        return toResponseData(userStack);
    }
    
    private StackData toResponseData(UserStack userStack) {
        return StackData.builder()
                .name(userStack.getStack().getName())
                .build();

    }
}
