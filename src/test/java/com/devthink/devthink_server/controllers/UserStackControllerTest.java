//package com.devthink.devthink_server.controllers;
//
//import com.devthink.devthink_server.application.AuthenticationService;
//import com.devthink.devthink_server.application.StackService;
//import com.devthink.devthink_server.application.UserService;
//import com.devthink.devthink_server.application.UserStackService;
//import com.devthink.devthink_server.domain.Stack;
//import com.devthink.devthink_server.domain.User;
//import com.devthink.devthink_server.domain.UserStack;
//import com.devthink.devthink_server.dto.UserStackRequestData;
//import com.devthink.devthink_server.security.UserAuthentication;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(UserStackController.class)
//class UserStackControllerTest {
//    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDk";
//
//    @Autowired
//    MockMvc mvc;
//
//    @MockBean
//    UserStackService userStackService;
//
//    @MockBean
//    AuthenticationService authenticationService;
//
//    @MockBean
//    UserService userService;
//
//    @MockBean
//    StackService stackService;
//
//    @BeforeEach
//    void setUp() {
//        UserStack userStack = UserStack.builder().build();
//
//        given(authenticationService.parseToken(VALID_TOKEN)).willReturn(1L);
//
//        given(userStackService.getUserStacks(1L)).willReturn(List.of(userStack));
//
//        given(userStackService.create(eq(1L), any(UserStackRequestData.class))).will(invocation -> {
//            User user = invocation.getArgument(0);
//            UserStackRequestData userStackRequestData = invocation.getArgument(1);
//            Stack stack = stackService.getStack(userStackRequestData.getStackId());
//            return UserStack.builder()
//                    .stack(stack)
//                    .user(user)
//                    .build();
//        });
//
//
//
//    }
//
//    @Test
//    void 특정_사용자스택을_조회하는_경우() throws Exception {
//        mvc.perform(get("/users/stacks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization","Bearer "+VALID_TOKEN)
//                )
//                .andExpect(status().isOk());
//    }
////
////    @Test
////    void 올바른_정보로_사용자스택을_생성하는_경우() throws Exception  {
////        mvc.perform(post("/users/stacks")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content("{\"name\":\"C/C++\"}")
////                        .header("Authorization","Bearer "+VALID_TOKEN)
////
////                )
////                .andExpect(status().isCreated());
////    }
//
//}
