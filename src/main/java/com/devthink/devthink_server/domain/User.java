package com.devthink.devthink_server.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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

    private String nickname;

    private String role;

    @Builder.Default
    @ElementCollection(targetClass = String.class)
    private List<String> stack = new ArrayList<>();

    @Builder.Default
    private String blogAddr = "";

    @Builder.Default
    private String gitNickname = "";

    @Builder.Default
    private Integer point = 0;

    @Builder.Default
    private String status = "active";

    public void changeWith(User source) {
        nickname = source.getNickname();
        role = source.getRole();
        stack = source.getStack();
        gitNickname = source.getGitNickname();
    }
}
