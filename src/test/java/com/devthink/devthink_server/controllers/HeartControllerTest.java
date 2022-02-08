package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.application.HeartService;
import com.devthink.devthink_server.application.PostService;
import com.devthink.devthink_server.application.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(HeartController.class)
class HeartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    HeartService heartService;

    @MockBean
    UserService userService;

    @MockBean
    PostService postService;


    @BeforeEach
    void setUp() {

    }






}
