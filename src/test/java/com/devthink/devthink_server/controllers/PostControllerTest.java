package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.dto.PostDto;
import com.devthink.devthink_server.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mvc;

    private final Long NOT_EXISTED_ID = 2L;

    @MockBean
    private PostService postService;

    @BeforeEach
    void setup(){
        given(postService.savePost(any(PostDto.class))).will(invocation ->{
            PostDto postDto = invocation.getArgument(0);

            return Post.builder()
                    .user_id(1L)
                    .category_id(1L)
                    .title(postDto.getTitle())
                    .content(postDto.getContent())
                    .status(postDto.getStatus())
                    .build();
        });

        given(postService.update(eq(1L), any(PostDto.class)))
                .will(invocation -> {
                   Long id = invocation.getArgument(0);
                   PostDto postDto = invocation.getArgument(1);

                    return Post.builder()
                            .title(postDto.getTitle())
                            .content(postDto.getContent())
                            .status(postDto.getStatus())
                            .build();

                });

        given(postService.getPost(eq(1L)))
                .will(invocation -> {
                    Long id = invocation.getArgument(0);

                    return Post.builder()
                            .title("test")
                            .content("test2")
                            .status("active")
                            .build();

                });


    }

    @Test
    void 올바른_정보로_글을_불러오는_경우() throws Exception{
        mvc.perform(
                get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test")))
                .andExpect(content().string(containsString("test2")))
                .andExpect(content().string(containsString("active")));

        verify(postService).getPost(eq(1L));
    }
    @Test
    void 올바른_정보로_글을_쓰려는_경우() throws Exception{
         mvc.perform(
                 post("/posts/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"tester\"," +
                                "\"content\":\"Tester\",\"status\":\"active\"}")

                )
                 .andExpect(status().isOk())
                 .andExpect(content().string(containsString("\"title\":\"tester\"")))
                 .andExpect(content().string(containsString("Tester")))
                 .andExpect(content().string(containsString("active")));


        verify(postService).savePost(any(PostDto.class));
    }
    @Test
     void 올바르지_않은_정보로_글을_쓰려는_경우() throws Exception {
        mvc.perform(
                post("/posts/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        )
                .andExpect(status().isBadRequest());

    }

    @Test
    void 올바른_정보로_글을_수정하려는_경우() throws Exception{
        mvc.perform(
                        put("/posts/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"new\"," +
                                        "\"content\":\"new1\",\"status\":\"new2\"}")

                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"title\":\"new\"")))
                .andExpect(content().string(containsString("new1")))
                .andExpect(content().string(containsString("new2")));


        verify(postService).update(eq(1L), any(PostDto.class));
    }



    @Test
    void 존재하는_게시글을_삭제하는_경우() throws Exception{
        mvc.perform(
                delete("/posts/1"))
                        .andExpect(status().isOk());

            verify(postService).deletePost(1L);

    }



}
