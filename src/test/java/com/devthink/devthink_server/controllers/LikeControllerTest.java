package com.devthink.devthink_server.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
class LikeControllerTest {

    private MockMvc mvc;

    @Test
    void detailWithValidUserIdAndValidPostId() throws Exception {
        mvc.perform(get("/likes"))
                .andExpect(status().isOk());
    }

}
