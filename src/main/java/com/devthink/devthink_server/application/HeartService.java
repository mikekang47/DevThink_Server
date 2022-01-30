package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.*;
import com.devthink.devthink_server.infra.LikeRepository;
import com.devthink.devthink_server.infra.PostRepository;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class HeartService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final Mapper mapper;

    public HeartService(LikeRepository likeRepository, PostRepository postRepository, CommentRepository commentRepository, Mapper mapper) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    public Heart createPostHeart(Post post, User user) {
        Long postId = post.getId();
        Long userId = user.getId();

        post.updateHeart(post.getHeartCnt() + 1);
        postRepository.save(post);

        Heart heart = Heart.builder()
                .postId(postId)
                .userId(userId)
                .build();

        return likeRepository.save(heart);


    }

    public Heart createCommentHeart(Comment comment, User user) {

        comment.updateHeart(comment.getHeartCnt() + 1);
        commentRepository.save(comment);

        Heart heart = Heart.builder()
                .commentId(comment.getId())
                .userId(user.getId())
                .build();

        return likeRepository.save(heart);


    }

    public void deletePostHeart(Long id) {
        likeRepository.deleteById(id);
    }
}
