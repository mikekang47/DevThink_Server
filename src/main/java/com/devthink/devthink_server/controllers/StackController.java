package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.StackService;
import com.devthink.devthink_server.domain.Stack;
import com.devthink.devthink_server.dto.StackData;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 스택의 HTTP 요청을 처리하는 클래스입니다.
 */
@RestController
@RequestMapping("/stacks")
@RequiredArgsConstructor
public class StackController {
    private final StackService stackService;

    /**
     * 저장된 모든 스택을 반환합니다.
     * @return 저장된 스택
     */
    @GetMapping
    @ApiOperation(value="스택 전체 조회", notes = "저장된 모든 스택을 불러옵니다.")
    public List<Stack> list() {
        return stackService.getStacks();
    }

    /**
     * 전달받은 스택 정보로 새로운 스택을 생성고 생성된 스택을 반환합니다.
     * @param stackData 스택정보
     * @return 생성된 스택
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "스택 등록", notes = "새로운 스택을 생성합니다.")
    public StackData registerStack(@RequestBody StackData stackData) {
         Stack stack = stackService.register(stackData);
         return StackData.builder().name(stack.getName()).build();

    }


}
