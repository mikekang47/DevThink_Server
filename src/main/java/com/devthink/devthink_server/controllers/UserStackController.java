package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.application.AuthenticationService;
import com.devthink.devthink_server.application.UserStackService;
import com.devthink.devthink_server.domain.UserStack;
import com.devthink.devthink_server.dto.StackData;
import com.devthink.devthink_server.dto.UserStackData;
import com.devthink.devthink_server.security.UserAuthentication;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final AuthenticationService authenticationService;

    /**
     * 전달받은 사용자 식별자로 사용자의 스택을 조회힙니다.
     * @param accessToken 사용자 토큰
     * @return 사용자의 스택
     */
    @GetMapping("/{accessToken}")
    @ApiOperation(value = "사용자 스택 조회", notes="사용자의 스택을 조회합니다.")
    @ApiImplicitParam(name="userId", dataType = "Long", value = "사용자 식별자")
    public List<StackData> detail(@PathVariable String accessToken) {
        Long userId = authenticationService.parseToken(accessToken);

        List<UserStack> sources = userStackService.getUserStacks(userId);
        List<StackData> stacks = new ArrayList<>();
        for(UserStack stack : sources) {
            stacks.add(getUserStackData(stack));
        }
        return stacks;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "사용자 스택 생성", notes = "사용자의 스택을 생성합니다.")
    public StackData createUserStack(@RequestBody @Valid UserStackData userStackData) {
        Long userId = authenticationService.parseToken(userStackData.getAccessToken());
        UserStack userStack = userStackService.create(userId, userStackData);
        return getUserStackData(userStack);
    }

    @PatchMapping("/{accessToken}")
    @ApiOperation(value = "사용자 스택 수정", notes = "사용자의 스택을 수정합니다.")
    public StackData updateUserStack(@PathVariable String accessToken, @RequestBody @Valid UserStackData userStackData) {
        Long userId = authenticationService.parseToken(accessToken);
        UserStack userStack = userStackService.update(userId, userStackData);
        return getUserStackData(userStack);
    }

    private StackData getUserStackData(UserStack userStack) {
        return StackData.builder()
                .name(userStack.getStack().getName())
                .build();

    }

}
