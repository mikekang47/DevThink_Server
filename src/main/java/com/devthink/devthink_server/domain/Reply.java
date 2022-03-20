package com.devthink.devthink_server.domain;

import com.devthink.devthink_server.dto.ReplyResponseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private String content;

    private String status;

    @Builder.Default
    Integer heartCnt = 0;

    @OneToMany(mappedBy = "reply")
    @Builder.Default
    private final List<ReplyHeart> hearts = new ArrayList<>();

    public ReplyResponseData toReplyResponseData() {
        return ReplyResponseData.builder()
                .replyId(id)
                .userProfile(user.toUserProfileData())
                .content(content)
                .heartPresent(hearts.stream().anyMatch(heart -> heart.getUser().equals(user)))
                .heartCnt(heartCnt)
                .createAt(getCreateAt())
                .updateAt(getUpdateAt())
                .build();
    }

    public void updateHeart(int heartCnt) {
        this.heartCnt = heartCnt;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
