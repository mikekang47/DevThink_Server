package com.devthink.devthink_server.domain.posts;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.infra.PostRepository;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @AfterEach
    public void cleanup(){
        postRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        Long user_id = 1L;
        Long category_id = 1L;
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String status = "테스트";

        postRepository.save(Post.builder()
                .user_id(user_id)
                .category_id(category_id)
                .title(title)
                .content(content)
                .status(status)
                .build());

        //when
        List<Post> postList = postRepository.findAll();

        //then
        Post post = postList.get(0);
        assertThat(post.getUser_id()).isEqualTo(user_id);
        assertThat(post.getCategory_id()).isEqualTo(category_id);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getStatus()).isEqualTo(status);
    }
}
