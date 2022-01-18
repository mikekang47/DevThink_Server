package com.devthink.devthink_server.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String role;

    @ElementCollection(targetClass = String.class)
    private List<String> stack;

    @Builder.Default
    private String blogAddr = "";

    @Builder.Default
    private String gitNickname = "";

    @Builder.Default
    private Integer point = 0;

    @Builder.Default
    private boolean deleted = false;

    @Builder.Default
    private Integer reported = 0;

    public void changeWith(User source) {
        nickname = source.getNickname();
        role = source.getRole();
        stack = source.getStack();
        gitNickname = source.getGitNickname();
    }

    public void destroy() {
        deleted = true;
    }

    public boolean authenticate(String password) {
        return !deleted && password.equals(this.password);
    }
}
