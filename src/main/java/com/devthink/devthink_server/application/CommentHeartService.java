package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Comment;
import com.devthink.devthink_server.domain.CommentHeart;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.errors.CommentNotFoundException;
import com.devthink.devthink_server.errors.HeartAlreadyExistsException;
import com.devthink.devthink_server.errors.HeartNotFoundException;
import com.devthink.devthink_server.errors.UserNotFoundException;
import com.devthink.devthink_server.infra.CommentHeartRepository;
import com.devthink.devthink_server.infra.CommentRepository;
import com.devthink.devthink_server.infra.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentHeartService {
    private final CommentHeartRepository commentHeartRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentHeartService(CommentHeartRepository commentHeartRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.commentHeartRepository = commentHeartRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * 전달받은 댓글 식별자와 사용자 식별자로 좋아요를 생성합니다.
     *
     * @param commentId 댓글 식별자
     * @param userId    사용자 식별자
     * @return 생성된 댓글 좋아요 객체
     */
    public CommentHeart createCommentHeart(Long commentId, Long userId) {
        User user = findUser(userId);
        Comment comment = findComment(commentId);

        if (commentHeartRepository.existsByCommentIdAndUserId(commentId, userId)) {
            throw new HeartAlreadyExistsException();
        }

        comment.updateHeart(comment.getHeartCnt() + 1);
        CommentHeart commentHeart = CommentHeart.builder().user(user).comment(comment).build();
        return commentHeartRepository.save(commentHeart);
    }

    public void destroyCommentHeart(Long commentId) {
        Comment comment = findComment(commentId);
        CommentHeart commentHeart = commentHeartRepository.findByCommentId(commentId)
                .orElseThrow(HeartNotFoundException::new);

        comment.updateHeart(comment.getHeartCnt() - 1);
        commentHeartRepository.deleteById(commentHeart.getId());
    }

    private User findUser(Long userId) {
        return userRepository.findByIdAndDeletedIsFalse(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }
}
