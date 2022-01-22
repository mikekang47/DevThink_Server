package com.devthink.devthink_server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Like extends BaseTimeEntity{
    @Id
    @GeneratedValue
    private Long id;
    
    private Long userId;

    private Long postId;


}
