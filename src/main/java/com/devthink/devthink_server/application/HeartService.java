package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.*;
import com.devthink.devthink_server.errors.*;
import com.devthink.devthink_server.infra.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;

    /**
     * 전달받은 식별자로 좋아요의 정보를 가져오고, 없을경우 예외를 발생합니다.
     * @param id 조회하려는 좋아요의 식별자
     * @return 생성된 좋아요 객체
     */
    public Heart getHeart(Long id) {
        return heartRepository.findById(id)
                .orElseThrow(() -> new HeartNotFoundException(id));
    }

    /**
     * 전달받은 게시글 식별자와 사용자 식별자로 좋아요를 생성합니다.
     * @param post 좋아요 누를 게시글
     * @param user 좋아요 누른 사용자
     * @return 생성된 게시글 좋아요 객체
     */
    public Heart createPostHeart(Post post, User user) {
        post.updateHeart(post.getHeartCnt()+1);
        Heart heart = Heart.builder().user(user).post(post).build();
        return heartRepository.save(heart);
    }

    /**
     * 전달받은 댓글 식별자와 사용자 식별자로 좋아요를 생성합니다.
     * @param comment 댓글 식별자
     * @param user 사용자 식별자
     * @return 생성된 댓글 좋아요 객체
     */
    public Heart createCommentHeart(Comment comment, User user) {
        comment.updateHeart(comment.getHeartCnt()+1);
        Heart heart = Heart.builder().user(user).comment(comment).build();
        return heartRepository.save(heart);
    }

    /**
     * 전달받은 리뷰 객체와 사용자 객체로 좋아요를 생성합니다.
     * @param review 리뷰 객체
     * @param user 사용자 객체
     * @return 생성된 댓글 좋아요 객체
     */
    public Heart createReviewHeart(Review review, User user) {
        review.updateHeart(review.getHeartCnt()+1);
        Heart heart = Heart.builder().user(user).review(review).build();
        return heartRepository.save(heart);
    }

    /**
     * 전달받은 좋아요 식별자를 찾아 삭제합니다.
     * @param id 삭제하고자하는 좋아요 식별자
     */
    public void destroyPostHeart(Long id) {
        heartRepository.deleteById(id);
    }

}
