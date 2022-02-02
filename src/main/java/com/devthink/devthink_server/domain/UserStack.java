package com.devthink.devthink_server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserStack {
    @Id
    @GeneratedValue
    Long id;

    @Column(name="user_id")
    Long userId;

    @Column(name="stack_id")
    Long stackId;
}
