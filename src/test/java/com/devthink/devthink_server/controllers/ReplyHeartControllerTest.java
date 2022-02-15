package com.devthink.devthink_server.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReplyHeartController.class)
class ReplyHeartControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void createReplyHeartWithValidReplyId() throws Exception {
        mvc.perform(post("/replies/hearts/1"))
                .andExpect(status().isCreated());
    }

}
