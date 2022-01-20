package com.devthink.devthink_server.domain;

import lombok.*;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Letter extends BaseTimeEntity{

    @Id
    @GeneratedValue
    private Long id;

    private Long roomId;

    private Long userId;

    private String content;

    private LocalDateTime view_at;


}