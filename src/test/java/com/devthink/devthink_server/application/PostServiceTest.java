package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Category;
import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.PostRequestData;
import com.devthink.devthink_server.errors.PostNotFoundException;
import com.devthink.devthink_server.infra.PostReportRepository;
import com.devthink.devthink_server.infra.PostRepository;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PostServiceTest {

    private final Long NOT_EXISTED_ID = 2L;

    private PostService postService;
    private PostRepository postRepository = mock(PostRepository.class);
    private PostReportRepository postReportRepository = mock(PostReportRepository.class);

    @BeforeEach
    void setup(){
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        postService = new PostService(postRepository, postReportRepository, mapper);


        given(postRepository.save(any(Post.class))).will(invocation -> {
            User user = User.builder().id(1L).build();
            Category category = Category.builder().id(1L).build();

            Post post = Post.builder()
                    .user(user)
                    .category(category)
                    .title("테스트")
                    .content("테스트")
                    .build();
            return post;
        });


        given(postRepository.findById(NOT_EXISTED_ID))
                .willThrow(new PostNotFoundException(NOT_EXISTED_ID));

    }

    @Test
    void 올바른_정보로_글을_쓰려는_경우() {
        User user = User.builder().id(1L).build();
        Category category = Category.builder().id(1L).build();

        PostRequestData postRequestData = PostRequestData.builder()
                .categoryId(1L)
                .title("테스트")
                .content("테스트")
                .imageUrl("test.com")
                .build();

        Post post = postService.savePost(user, category, postRequestData);

        assertThat(post.getUser().getId()).isEqualTo(1L);
        assertThat(post.getContent()).isEqualTo("테스트");

    }

    @Test
    void 올바른_정보로_글을_수정하려는_경우(){
        User user = User.builder().id(1L).build();
        Category category = Category.builder().id(1L).build();
        PostRequestData postRequestData = PostRequestData.builder()
                .categoryId(1L)
                .title("test22")
                .content("test22")
                .imageUrl("test.com")
                .build();

        Post postData = Post.builder().id(1L).user(user).category(category).build();
        Post post = postService.update(user, postData, postRequestData);

        assertThat(post.getUser().getId()).isEqualTo(1L);
        assertThat(post.getTitle()).isEqualTo("test22");
        assertThat(post.getContent()).isEqualTo("test22");

    }

}
