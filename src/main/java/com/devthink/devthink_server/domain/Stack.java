package com.devthink.devthink_server.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Stack {
    @Id
    @GeneratedValue
    Long id;

    String name;
}
