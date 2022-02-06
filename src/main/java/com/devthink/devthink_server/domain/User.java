package com.devthink.devthink_server.domain;


import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@AllArgsConstructor
@Entity
@Builder
@NoArgsConstructor
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String phoneNum;

    private String role;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Stack> stack;

    private String blogAddr;

    private String gitNickname;

    private Integer point;

    @Builder.Default
    private String imageUrl = "";

    @Builder.Default
    private boolean deleted = false;

    @Builder.Default
    private Integer reported = 0;

    public void changeWith(User source) {
        nickname = source.getNickname();
        role = source.getRole();
        stack = source.getStack();
        imageUrl = source.getImageUrl();
        gitNickname = source.getGitNickname();
        blogAddr = source.getBlogAddr();
        password = source.getPassword();
        point = source.getPoint();
    }

    public void destroy() {
        deleted = true;
    }

    public boolean authenticate(String password) {
        return !deleted && password.equals(this.password);
    }

}

