package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.dto.PostDto;
import com.devthink.devthink_server.infra.PostRepository;
import com.devthink.devthink_server.service.PostService;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PostControllerTest {

    private PostService postService;
    private PostRepository postRepository = mock(PostRepository.class);

    @BeforeEach
    void setup(){
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        postService = new PostService(postRepository, mapper);

        given(postRepository.save(any(Post.class))).will(invocation -> {
            Post post = Post.builder()
                    .user_id(1L)
                    .category_id(1L)
                    .title("test")
                    .content("test")
                    .status("active")
                    .build();
            return post;
        });


    }

    @Test
    void 올바른_정보로_글을_쓰려는_경우() {
        PostDto postDto = PostDto.builder()
                .user_id(1L)
                .category_id(1L)
                .title("테스트")
                .content("테스트")
                .build();

        Post post = postService.savePost(postDto);

        assertThat(post.getUser_id()).isEqualTo(1L);
        assertThat(post.getStatus()).isEqualTo("active");
        verify(postRepository).save(any(Post.class));

    }


}
