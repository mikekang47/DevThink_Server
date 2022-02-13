package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.domain.PostHeart;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.errors.PostNotFoundException;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.devthink.devthink_server.infra.PostHeartRepository;
import com.devthink.devthink_server.infra.PostRepository;
import com.devthink.devthink_server.infra.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PostHeartService {
    private final UserRepository userRepository;
    private final PostHeartRepository postheartRepository;
    private final PostRepository postRepository;

    public PostHeartService(UserRepository userRepository, PostHeartRepository postheartRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postheartRepository = postheartRepository;
        this.postRepository = postRepository;
    }

    public PostHeart createPostHeart(Long postId, Long userId) {
        Post post = findPost(postId);
        post.updateHeart(post.getHeartCnt()+1);
        User user = findUser(userId);
        PostHeart postHeart = PostHeart.builder().user(user).post(post).build();
        return postheartRepository.save(postHeart);
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    private User findUser(Long userId) {
        return userRepository.findByIdAndDeletedIsFalse(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }


}
