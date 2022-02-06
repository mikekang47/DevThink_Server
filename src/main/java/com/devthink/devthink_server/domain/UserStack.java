package com.devthink.devthink_server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStack {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(targetEntity = Stack.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "stack_id")
    Stack stack;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
}
