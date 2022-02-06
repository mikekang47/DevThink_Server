package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.application.UserStackService;
import com.devthink.devthink_server.domain.UserStack;
import com.devthink.devthink_server.dto.StackData;
import com.devthink.devthink_server.dto.UserStackData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자의 스택에 관한 클래스입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/stacks")
public class UserStackController {
    private final UserStackService userStackService;

    /**
     * 전달받은 사용자 식별자로 사용자의 스택을 조회힙니다.
     * @param userId 사용자 식별자
     * @return 사용자의 스택
     */
    @GetMapping("/{userId}")
    @ApiOperation(value = "사용자 스택 조회", notes="사용자의 스택을 조회합니다.")
    @ApiImplicitParam(name="userId", dataType = "Long", value = "사용자 식별자")
    public List<StackData> detail(@PathVariable Long userId) {
        List<UserStack> sources = userStackService.getUserStacks(userId);
        List<StackData> stacks = new ArrayList<>();
        for(UserStack stack : sources) {
            stacks.add(getUserStackData(stack));
        }
        return stacks;
    }

    @PostMapping("/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "사용자 스택 생성", notes = "사용자의 스택을 생성합니다.")
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
