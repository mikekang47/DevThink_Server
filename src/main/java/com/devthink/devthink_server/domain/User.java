package com.devthink.devthink_server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue
    Long id;

    String email;

    String nickName;

    String phoneNum;

    String role;

    String stack;

    String password;

    String point;

    String git_NickName;

    String status;



}
