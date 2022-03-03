package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.AuthenticationService;
import com.devthink.devthink_server.application.CategoryService;
import com.devthink.devthink_server.application.PostService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.*;
import com.devthink.devthink_server.dto.PostRequestData;
import com.devthink.devthink_server.errors.PostNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    private final Long NOT_EXISTED_ID = 2L;

    @MockBean
    private PostService postService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @BeforeEach
    void setup(){

        given(postService.savePost(any(User.class), any(Category.class), any(PostRequestData.class))).will(invocation ->{
            User user = User.builder().id(1L).build();
            Category category = Category.builder().id(1L).build();
            PostRequestData postRequestData = invocation.getArgument(2);

            return Post.builder()
                    .user(user)
                    .category(category)
                    .title(postRequestData.getTitle())
                    .content(postRequestData.getContent())
                    .build();
        });

        given(userService.getUser(1L)).will(invocation -> {
            return User.builder()
                    .id(1L)
                    .build();
        });

        given(categoryService.getCategory(1L)).will(invocation -> {
            return Category.builder()
                    .id(1L)
                    .build();
        });

        given(postService.getPostById(eq(1L)))
                .will(invocation -> {
                    Long id = invocation.getArgument(0);
                    User user = User.builder().id(1L).build();
                    Category category = Category.builder().id(1L).build();
                    return Post.builder()
                            .id(id)
                            .user(user)
                            .category(category)
                            .title("test")
                            .content("test2")
                            .build();

                });


        given(postService.update(any(Post.class), any(PostRequestData.class)))
                .will(invocation -> {
                    Post post = invocation.getArgument(0);
                    PostRequestData postRequestData = invocation.getArgument(1);

                    return Post.builder()
                            .id(1L)
                            .user(post.getUser())
                            .category(post.getCategory())
                            .title(postRequestData.getTitle())
                            .content(postRequestData.getContent())
                            .build();

                });

        given(postService.getPostById(eq(100L)))
                .willThrow(new PostNotFoundException(100L));

        given(postService.deletePost(any(Post.class)))
                .will(invocation -> {
                    Post post = invocation.getArgument(0);
                    return Post.builder()
                            .id(1L)
                            .user(post.getUser())
                            .category(post.getCategory())
                            .title("test")
                            .content("test")
                            .deleted(true)
                            .build();
                });

    }

    @Test
    void 올바른_정보로_글을_불러오는_경우() throws Exception{
        mvc.perform(
                        get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test")))
                .andExpect(content().string(containsString("test2")));

        verify(postService).getPostById(eq(1L));
    }
    @Test
    void 올바른_정보로_글을_쓰려는_경우() throws Exception{

        mvc.perform(
                        post("/posts/write")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\": 1, \n\"categoryId\": 1," +
                                        "\n\"content\": \"test\", \n\"title\": \"test\"}"))
                .andExpect(status().isCreated());

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
                                        "\"content\":\"new1\"}")

                )
                .andExpect(status().isOk());

        verify(postService).update(any(Post.class), any(PostRequestData.class));

    }

    @Test
    void 존재하지_않는_글을_수정하려는_경우() throws Exception {
        mvc.perform(
                        put("/posts/100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"new\"," +
                                        "\"content\":\"new1\"}")
                )
                .andExpect(status().isNotFound());
    }



    @Test
    void 존재하는_게시글을_삭제하는_경우() throws Exception{
        mvc.perform(
                        delete("/posts/1"))
                .andExpect(status().isNoContent());

        verify(postService).deletePost(any(Post.class));

    }

    @Test
    void 존재하지_않는_게시글을_삭제하는_경우() throws Exception{

        mvc.perform(
                        delete("/posts/100"))
                .andExpect(status().isNotFound());

    }
}
