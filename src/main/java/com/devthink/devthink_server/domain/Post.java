package com.devthink.devthink_server.domain;

import com.devthink.devthink_server.dto.PostListData;
import com.devthink.devthink_server.dto.PostResponseData;
import com.devthink.devthink_server.dto.ReviewDetailResponseData;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;

    @ManyToOne(fetch = LAZY)
    private Category category;

    private boolean image;

    private String title;

    @ManyToOne(fetch = LAZY)
    private PostHeart heart;

    @Builder.Default
    private String subTitle = ""; // 프로젝트 구인글 용

    private String content;

    @Builder.Default
    private String imageUrl = "";

    @Builder.Default
    private Boolean deleted = false;

    @Builder.Default
    private Integer heartCnt = 0;

    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public void update(String subTitle, String title, String content) {
        this.subTitle = subTitle;
        this.title = title;
        this.content = content;
    }

    public void updateHeart(int heartCnt) {
        this.heartCnt = heartCnt;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public PostListData toPostListData() {
        return PostListData.builder()
                .categoryId(category.getId())
                .userProfile(user.toUserProfileData())
                .subTitle(subTitle)
                .content(content)
                .title(title)
                .deleted(deleted)
                .createAt(getCreateAt())
                .updateAt(getUpdateAt())
                .heartCnt(heartCnt)
                .id(id)
                .imageUrl(imageUrl)
                .Image(isImage())
                .build();
    }

    public PostResponseData toPostResponseData() {
        return PostResponseData.builder()
                .userProfile(user.toUserProfileData())
                .imageUrl(imageUrl)
                .id(id)
                .subTitle(subTitle)
                .categoryId(category.getId())
                .deleted(deleted)
                .createAt(getCreateAt())
                .heartCnt(heartCnt)
                .updateAt(getUpdateAt())
                .content(content)
                .Image(isImage())
                .title(title)
                .comments(comments.stream().map(Comment::toCommentDetailResponseData).collect(Collectors.toList()))
                .build();
    }

}
