package com.devthink.devthink_server.domain;

import com.devthink.devthink_server.dto.ReplyResponseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private Comment comment;

    private String content;

    private String status;

    public ReplyResponseData toReplyResponseData() {
        return ReplyResponseData.builder()
                .replyId(id)
                .userId(user.getId())
                .userNickname(user.getNickname())
                .userImageUrl(user.getImageUrl())
                .content(content)
                .createAt(getCreateAt())
                .updateAt(getUpdateAt())
                .build();
    }
}
