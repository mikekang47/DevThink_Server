package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PostController.class)
class PostControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    public PostService postService;


    @Test
    void list() throws Exception {
        Post post = Post.builder()
                        .user_id(1L)
                        .title("abc")
                        .content("abcd")
                        .status("active")
                        .build();
        given(postService.getPostList()).willReturn(List.of(post));

        mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("abc")))
                .andExpect(content().string(containsString("abcd")))
                .andExpect(content().string(containsString("active")));
    }

    @Test
    void detail() throws Exception {
        mvc.perform(get("/posts/1")
                .accept(MediaType.APPLICATION_JSON)
                        .content("{\"user_id\":\"1L\"}")
                )
                .andExpect(status().isOk());



    }

}
