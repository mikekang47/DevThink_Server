package com.devthink.devthink_server.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String nickname;

    private String role;

    @Column
    @ElementCollection(targetClass = String.class)
    private List<String> stack;
    
    private String blogAddr;

    private String gitNickname;

    private Integer point;
}
