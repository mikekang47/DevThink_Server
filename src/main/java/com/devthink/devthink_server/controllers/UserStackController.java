package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.application.AuthenticationService;
import com.devthink.devthink_server.application.UserStackService;
import com.devthink.devthink_server.domain.UserStack;
import com.devthink.devthink_server.dto.StackData;
import com.devthink.devthink_server.dto.UserStackRequestData;
import com.devthink.devthink_server.dto.UserStackResponseData;
import com.devthink.devthink_server.security.UserAuthentication;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/users/stacks")
public class UserStackController {
    private final UserStackService userStackService;
    private final AuthenticationService authenticationService;

    public UserStackController(UserStackService userStackService, AuthenticationService authenticationService) {
        this.userStackService = userStackService;
        this.authenticationService = authenticationService;
    }

    /**
     * 전달받은 사용자 식별자로 사용자의 스택을 조회힙니다.
     * @return 사용자의 스택
     */
    @GetMapping
    @ApiOperation(value = "사용자 스택 조회", notes="현재 사용자의 스택을 조회합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.")
    @PreAuthorize("isAuthenticated()")
    public List<UserStackResponseData> detail(UserAuthentication userAuthentication) {
        Long userId = userAuthentication.getUserId();

        List<UserStack> sources = userStackService.getUserStacks(userId);
        List<UserStackResponseData> stacks = new ArrayList<>();
        for(UserStack stack : sources) {
            stacks.add(getUserStackData(stack));
        }
        return stacks;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "사용자 스택 생성", notes = "현재 사용자의 스택을 생성하고 그 정보를 반환합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.")
    @ApiImplicitParam(name="유저 스택 생성 데이터", required = true)
    public UserStackResponseData createUserStack(@RequestBody @Valid UserStackRequestData userStackRequestData, UserAuthentication userAuthentication) {
        Long userId = userAuthentication.getUserId();
        UserStack userStack = userStackService.create(userId, userStackRequestData);
        return getUserStackData(userStack);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "사용자 스택 삭제", notes = "현재 사용자의 스택을 삭제할 수 있습니다. 헤더에 사용자 토큰 주입을 필요로 합니다.")
    @PreAuthorize("isAuthenticated()")
    @ApiImplicitParam(dataType="integer", name="사용자 스택 식별자", required = true)
    public void deleteUserStack(@PathVariable Long id,UserAuthentication userAuthentication) {
        userStackService.destroy(id);
    }

    private UserStackResponseData getUserStackData(UserStack userStack) {
        return UserStackResponseData.builder()
                .id(userStack.getId())
                .stackId(userStack.getStack().getId())
                .userId(userStack.getUser().getId())
                .build();

    }

}
