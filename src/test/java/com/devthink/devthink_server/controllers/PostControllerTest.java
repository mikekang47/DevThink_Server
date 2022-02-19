package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.AuthenticationService;
import com.devthink.devthink_server.application.CategoryService;
import com.devthink.devthink_server.application.PostService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.*;
import com.devthink.devthink_server.dto.PostRequestData;
import com.devthink.devthink_server.errors.CategoryNotFoundException;
import com.devthink.devthink_server.errors.PostNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.in;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;
    private final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.KiNUK70RDCTWeRMqfN6YY_SAkkb8opFsAh_fwAntt4";

    @BeforeEach
    void setup() {

        given(postService.getPosts(eq(1L))).will(invocation -> {
            User user = User.builder().id(1L).build();
            Category category = Category.builder().id(1L).build();
            Post post = Post.builder()
                    .id(1L)
                    .user(user)
                    .category(category)
                    .title("test")
                    .content("test")
                    .imageUrl("test.com")
                    .build();

            List<Post> postList = new ArrayList<>();
            postList.add(post);
            return postList;
        });

        given(postService.savePost(any(User.class), any(Category.class), any(PostRequestData.class))).will(invocation -> {
            User user = User.builder().id(1L).build();
            Category category = Category.builder().id(1L).build();
            PostRequestData postRequestData = invocation.getArgument(2);

            return Post.builder()
                    .user(user)
                    .category(category)
                    .title(postRequestData.getTitle())
                    .content(postRequestData.getContent())
                    .imageUrl(postRequestData.getImageUrl())
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


        given(postService.update(any(User.class), any(Post.class), any(PostRequestData.class)))
                .will(invocation -> {
                    User user = invocation.getArgument(0);
                    Post post = invocation.getArgument(1);
                    PostRequestData postRequestData = invocation.getArgument(2);

                    return Post.builder()
                            .id(1L)
                            .user(user)
                            .category(post.getCategory())
                            .title(postRequestData.getTitle())
                            .content(postRequestData.getContent())
                            .imageUrl(postRequestData.getImageUrl())
                            .build();
                });

        given(postService.search(eq(1L), eq("test")))
                .will(invocation -> {
                    User user = User.builder().id(1L).build();
                    Category category = Category.builder().id(1L).build();

                    Post post = Post.builder()
                            .id(1L)
                            .user(user)
                            .category(category)
                            .title("test")
                            .content("test")
                            .build();

                    List<Post> posts = new ArrayList<>();
                    posts.add(post);
                    return posts;
                });

        given(postService.getBestPost(any(Category.class)))
                .will(invocation -> {
                    User user = User.builder().id(1L).build();
                    Category category = invocation.getArgument(0);

                    Post post = Post.builder()
                            .id(1L)
                            .user(user)
                            .category(category)
                            .title("test")
                            .content("test")
                            .heartCnt(1)
                            .build();

                    List<Post> posts = new ArrayList<>();
                    posts.add(post);
                    return posts;
                });


        given(postService.getPostById(eq(100L)))
                .willThrow(new PostNotFoundException(100L));

        given(postService.getPosts(eq(100L)))
                .willThrow(new CategoryNotFoundException(100L));

        given(authenticationService.parseToken(VALID_TOKEN)).willReturn(1L);
    }

    @Test
    void 올바른_정보로_글을_불러오는_경우() throws Exception {
        mvc.perform(
                        get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test")))
                .andExpect(content().string(containsString("test2")));

        verify(postService).getPostById(eq(1L));
    }

    @Test
    void 올바른_정보로_글을_쓰려는_경우() throws Exception {

        mvc.perform(
                        post("/posts/write")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"categoryId\": 1," +
                                        "\n\"title\": \"test\", \n\"content\": \"test\", \n\"imageUrl\": \"test.com\"}")
                                .header("Authorization", "Bearer " + VALID_TOKEN)
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ))
                .andExpect(content().string(
                        containsString("\"title\":\"test\"")
                ))
                .andExpect(content().string(
                        containsString("\"content\":\"test\"")
                ))
                .andExpect(content().string(
                        containsString("\"imageUrl\":\"test.com\"")
                ));
    }

    @Test
    void 올바르지_않은_정보로_글을_쓰려는_경우() throws Exception {
        mvc.perform(
                        post("/posts/write")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                                .header("Authorization", "Bearer " + VALID_TOKEN)
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    void 올바른_정보로_글을_수정하려는_경우() throws Exception {
        mvc.perform(
                        put("/posts/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"new\"," +
                                        "\"content\":\"new1\"," +
                                        "\"imageUrl\":\"test.com\"}")
                                .header("Authorization", "Bearer " + VALID_TOKEN)

                )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ))
                .andExpect(content().string(
                        containsString("\"title\":\"new\"")
                ))
                .andExpect(content().string(
                        containsString("\"content\":\"new1\"")
                ))
                .andExpect(content().string(
                        containsString("\"imageUrl\":\"test.com\"")
                ));

        verify(postService).update(any(User.class), any(Post.class), any(PostRequestData.class));

    }

    @Test
    void 존재하지_않는_글을_수정하려는_경우() throws Exception {
        mvc.perform(
                        put("/posts/100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"new\"," +
                                        "\"content\":\"new1\"," +
                                        "\"imageUrl\":\"test.com\"}")
                                .header("Authorization", "Bearer " + VALID_TOKEN)
                )
                .andExpect(status().isNotFound());
    }


    @Test
    void 존재하는_게시글을_삭제하는_경우() throws Exception {
        mvc.perform(
                        delete("/posts/1")
                                .header("Authorization", "Bearer " + VALID_TOKEN)
                )
                .andExpect(status().isNoContent());

        verify(postService).deletePost(any(User.class), any(Post.class));

    }

    @Test
    void 존재하지_않는_게시글을_삭제하는_경우() throws Exception {

        mvc.perform(
                        delete("/posts/100")
                                .header("Authorization", "Bearer " + VALID_TOKEN)
                )
                .andExpect(status().isNotFound());

    }

    @Test
    void 올바른_정보로_카테고리별_게시글을_불러오는_경우() throws Exception {
        mvc.perform(
                        get("/posts/list/1")

                )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1")
                ))
                .andExpect(content().string(
                        containsString("\"title\":\"test\"")
                ))
                .andExpect(content().string(
                        containsString("\"content\":\"test\"")
                ))
                .andExpect(content().string(
                        containsString("\"imageUrl\":\"test.com\"")
                ));

        verify(postService).getPosts(1L);
    }

    @Test
    void 올바르지_않은_정보로_카테고리별_게시글을_불러오는_경우() throws Exception {
        mvc.perform(
                        get("/posts/list/100")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void 올바른_정보로_제목을_검색하는_경우() throws Exception {
        mvc.perform(
                        get("/posts/search/1").param("keyword", "test")

                )

                .andExpect(status().isOk())

                .andExpect(content().string(
                        containsString("\"title\":\"test\"")
                ));
        verify(postService).search(1L, "test");

    }

    @Test
    void 올바른_정보로_베스트_게시글을_가져오는_경우() throws Exception {

        mvc.perform(
                        get("/posts/best/1")
                )
                .andExpect(status().isOk())

                .andExpect(content().string(
                        containsString("\"heartCnt\":1")
                ));
        verify(postService).getBestPost(any(Category.class));
    }

}
