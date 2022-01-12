package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Comment;
import com.devthink.devthink_server.domain.CommentRepository;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.CommentRequestDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * 모든 Comment를 조회합니다.
     * @return
     */
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    /**
     * 입력된 comment 정보로 새로운 Comment를 생성합니다.
     * @param user Comment를 생성하려고 하는 User
     * @param review Comment가 달리게 되는 Review
     * @param commentRequestDto
     * @return 생성된 Comment의 id 값
     */
    public String createComment(User user, Review review, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.save(
                Comment.builder()
                        .user(user)
                        .review(review)
                        .content(commentRequestDto.getContent())
                        .build()
        );
        return comment.getId().toString();
    }
}
