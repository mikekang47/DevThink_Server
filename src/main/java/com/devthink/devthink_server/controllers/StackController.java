package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.StackService;
import com.devthink.devthink_server.domain.Stack;
import com.devthink.devthink_server.dto.StackData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stacks")
@RequiredArgsConstructor
public class StackController {
    private final StackService stackService;

    @GetMapping
    public List<Stack> list() {
        return stackService.getStacks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StackData registerStack(@RequestBody StackData stackData) {
         Stack stack = stackService.register(stackData);
         return StackData.builder().name(stack.getName()).build();

    }


}
