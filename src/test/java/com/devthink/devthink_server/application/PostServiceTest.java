package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Category;
import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.PostRequestData;
import com.devthink.devthink_server.errors.PostNotFoundException;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.devthink.devthink_server.errors.UserNotMatchException;
import com.devthink.devthink_server.infra.PostReportRepository;
import com.devthink.devthink_server.infra.PostRepository;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
            Post post  = invocation.getArgument(0);

            return Post.builder()
                    .user(user)
                    .category(category)
                    .title(post.getTitle())
                    .content(post.getContent())
                    .imageUrl(post.getImageUrl())
                    .build();
        });


        given(postRepository.findById(NOT_EXISTED_ID))
                .willThrow(new PostNotFoundException(NOT_EXISTED_ID));

        given(postRepository.findByIdAndDeletedIsFalse(1L)).will(invocation -> {
            User user = User.builder().id(1L).build();
            Category category = Category.builder().id(1L).build();

            return Optional.of(Post.builder()
                    .user(user)
                    .category(category)
                    .title("test")
                    .content("test")
                    .build());
        });

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
        assertThat(post.getTitle()).isEqualTo("테스트");
        assertThat(post.getContent()).isEqualTo("테스트");
        assertThat(post.getImageUrl()).isEqualTo("test.com");

        verify(postRepository).save(any(Post.class));

    }

    @Test
    void 올바른_유저_정보로_글을_수정하려는_경우(){
        User user = User.builder().id(1L).build();
        Category category = Category.builder().id(1L).build();

        PostRequestData postRequestData = PostRequestData.builder()
                .categoryId(1L)
                .subTitle("test")
                .title("test22")
                .content("test22")
                .build();

        Post postData = Post.builder().id(1L).user(user).category(category).build();
        Post post = postService.update(user, postData, postRequestData);

        assertThat(post.getUser().getId()).isEqualTo(1L);
        assertThat(post.getTitle()).isEqualTo("test22");
        assertThat(post.getContent()).isEqualTo("test22");
        assertThat(post.getSubTitle()).isEqualTo("test");

    }

    @Test
    void 올바르지_않은_유저_정보로_글을_수정하려는_경우(){

        User invalidUser = User.builder().id(100L).build();
        User user = User.builder().id(1L).build();
        Category category = Category.builder().id(1L).build();

        PostRequestData postRequestData = PostRequestData.builder()
                .categoryId(1L)
                .subTitle("test")
                .title("test22")
                .content("test22")
                .build();

        Post post = Post.builder().id(1L).user(user).category(category).build();

        assertThatThrownBy(
                () -> postService.update(invalidUser, post, postRequestData)
        )
                .isInstanceOf(UserNotMatchException.class);

    }

    @Test
    void 올바른_유저로_글을_삭제하려는_경우(){
        User user = User.builder().id(1L).build();
        Category category = Category.builder().id(1L).build();

        Post post = Post.builder()
                .id(1L)
                .user(user)
                .category(category)
                .title("test")
                .content("test")
                .build();

        postService.deletePost(user, post);
        assertThat(post.getDeleted()).isEqualTo(true);
    }

    @Test
    void 올바르지_않은_유저로_글을_삭제하려는_경우(){

        User invalidUser = User.builder().id(100L).build();
        User user = User.builder().id(1L).build();
        Category category = Category.builder().id(1L).build();

        Post post = Post.builder()
                .id(1L)
                .user(user)
                .category(category)
                .title("test")
                .content("test")
                .build();

        assertThatThrownBy(
                () -> postService.deletePost(invalidUser, post)
        )
                .isInstanceOf(UserNotMatchException.class);

    }

}
