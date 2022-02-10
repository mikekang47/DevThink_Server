package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.AuthenticationService;
import com.devthink.devthink_server.application.StackService;
import com.devthink.devthink_server.domain.Stack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StackController.class)
class StackControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    StackService stackService;


    @BeforeEach
    void setUp() {
        Stack stack = new Stack(1L, "C/C++");
        given(stackService.getStacks()).willReturn(List.of(stack));
    }
    
    @Test
    void 모든_스택을_불러오는_경우() throws Exception {
        mvc.perform(get("/stacks"))
                .andExpect(content().string(containsString("C/C++")))
                .andExpect(status().isOk());
    }
}
