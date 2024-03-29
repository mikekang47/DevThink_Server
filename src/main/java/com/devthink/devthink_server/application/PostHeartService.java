package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.domain.PostHeart;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.errors.HeartAlreadyExistsException;
import com.devthink.devthink_server.errors.HeartNotFoundException;
import com.devthink.devthink_server.errors.PostNotFoundException;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.devthink.devthink_server.infra.PostHeartRepository;
import com.devthink.devthink_server.infra.PostRepository;
import com.devthink.devthink_server.infra.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PostHeartService {
    private final UserRepository userRepository;
    private final PostHeartRepository postHeartRepository;
    private final PostRepository postRepository;

    public PostHeartService(UserRepository userRepository, PostHeartRepository postHeartRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postHeartRepository = postHeartRepository;
        this.postRepository = postRepository;
    }

    public Boolean checkPostHeart(Long postId, Long userId) {
        findPost(postId);
        findUser(userId);

        if(postHeartRepository.findByPostIdAndUserId(postId, userId).isPresent()) {
            return true;
        } else{
            return false;
        }

    }

    public PostHeart createPostHeart(Long postId, Long userId) {
        Post post = findPost(postId);
        User user = findUser(userId);

        if(postHeartRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new HeartAlreadyExistsException();
        }

        post.updateHeart(post.getHeartCnt() + 1);
        PostHeart postHeart = PostHeart.builder()
                .user(user)
                .post(post)
                .build();
        return postHeartRepository.save(postHeart);
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    private User findUser(Long userId) {
        return userRepository.findByIdAndDeletedIsFalse(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }


    /**
     * 전달받은 좋아요 식별자를 찾아 삭제합니다.
     * @param postId 삭제하고자하는 게시글 식별자
     */
    public void destroyPostHeart(Long postId, Long userId) {
        Post post = findPost(postId);
        PostHeart postHeart = postHeartRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new HeartNotFoundException());

        post.updateHeart(post.getHeartCnt()-1);
        postHeartRepository.deleteById(postHeart.getId());
    }
}
