package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.infra.PostRepository;
import com.devthink.devthink_server.service.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class TestJpaPostControllerTest {
    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Test
    void 게시글_불러오기() throws Exception{
        Post post = Post.builder()
                .user_id(1L)
                .category_id(1L)
                .content("테스트1")
                .title("테스트1")
                .status("테스트1")
                .build();

        given(postRepository.findById(1L))
                .willReturn(Optional.of(post));

        Optional<Post> member = postService.getPost(1L);

        if(member.isPresent()){
            Assertions.assertThat(member.get().getContent()).isEqualTo(post.getContent());
            Assertions.assertThat(member.get().getTitle()).isEqualTo(post.getTitle());
            Assertions.assertThat(member.get().getStatus()).isEqualTo(post.getStatus());

        }

    }


}
