package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.AuthenticationService;
import com.devthink.devthink_server.application.ReplyHeartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReplyHeartController.class)
class ReplyHeartControllerTest {
    private final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjF9.KiNUK70RDCTWeRMqfN6YY_SAkkb8opFsAh_fwAntt4";

    @Autowired
    MockMvc mvc;

    @MockBean
    ReplyHeartService replyHeartService;

    @MockBean
    AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void createReplyHeartWithValidReplyId() throws Exception {
        mvc.perform(post("/replies/hearts/1")
                        .header("Authorization","Bearer " + VALID_TOKEN)

                )
                .andExpect(status().isCreated());
    }

}
