package com.devthink.devthink_server.domain;

import com.devthink.devthink_server.dto.CommentDetailResponseData;
import com.devthink.devthink_server.dto.CommentResponseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = Post.class,fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(targetEntity = Review.class, fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = LAZY)
    private CommentHeart heart;

    private String content;

    private String status;

    @Builder.Default
    Integer heartCnt = 0;

    @OneToMany(mappedBy = "comment")
    @Builder.Default
    private final List<Reply> replys = new ArrayList<>();

    public CommentResponseData toCommentResponseData() {
        return CommentResponseData.builder()
                .commentId(id)
                .userProfile(user.toUserProfileData())
                .content(content)
                .createAt(getCreateAt())
                .updateAt(getUpdateAt())
                .build();
    }

    /**
     * 답글 리스트가 추가 된 댓글 상세 정보로 변환합니다.
     * @return 변환 된 CommentDetailResponseData 객체
     */
    public CommentDetailResponseData toCommentDetailResponseData() {
        return CommentDetailResponseData.builder()
                .comment(toCommentResponseData())
                .replys(replys.stream().map(Reply::toReplyResponseData).collect(Collectors.toList()))
                .build();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void updateHeart(int heartCnt) {
        this.heartCnt = heartCnt;
    }

}


