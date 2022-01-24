package com.devthink.devthink_server.domain;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Letter extends BaseTimeEntity{

    @Id
    @GeneratedValue
    private Long id;

    private Long roomId;    // 방 번호

    private Long senderId;  // 발신자

    private Long targetId;  // 수신자

    private String content; // 쪽지 내용

    private LocalDateTime view_at; // 열람일자

    private Integer readCheck;  // 수신 확인

}