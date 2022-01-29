package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.*;
import com.devthink.devthink_server.errors.CommentNotFoundException;
import com.devthink.devthink_server.errors.LikeNotFoundException;
import com.devthink.devthink_server.errors.PostIdNotFoundException;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.devthink.devthink_server.infra.LikeRepository;
import com.devthink.devthink_server.infra.PostRepository;
import com.devthink.devthink_server.infra.UserRepository;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final Mapper mapper;

    public LikeService(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository, Mapper mapper) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    public Like createPostLike(Post post, User user) {
        Long postId = post.getId();
        Long userId = user.getId();

        post.updateHeart(post.getHeart() + 1);
        postRepository.save(post);

        Like like = Like.builder()
                .postId(postId)
                .userId(userId)
                .build();

        return likeRepository.save(like);


    }

    public Like createCommentLike(Comment comment, User user) {

        comment.updateHeart(comment.getHeart() + 1);
        commentRepository.save(comment);

        Like like = Like.builder()
                .commentId(comment.getId())
                .userId(user.getId())
                .build();

        return likeRepository.save(like);


    }

    public void deletePostLike(Long id) {
        likeRepository.deleteById(id);
    }
}
