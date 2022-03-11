package com.devthink.devthink_server.domain;

import com.devthink.devthink_server.dto.LetterListData;
import com.devthink.devthink_server.dto.LetterResultData;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Letter extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    private UserRoom room;    // 방 번호

    @ManyToOne(fetch = LAZY)
    private User sender;  // 발신자

    @ManyToOne(fetch = LAZY)
    private User target;  // 수신자

    private String content; // 쪽지 내용

    @Builder.Default
    private boolean readCheck = false;

    @Builder.Default
    private boolean heart = false;  // 하트 기능

    public void setReadCheck(boolean readCheck) {
        this.readCheck = readCheck;
    }

    public LetterResultData toLetterResultData() {
        return LetterResultData.builder()
                .readCheck(readCheck)
                .createAt(getCreateAt())
                .content(content)
                .senderId(sender.getId())
                .targetId(target.getId())
                .heart(isHeart())
                .targetNick(target.getNickname())
                .sendNick(sender.getNickname())
                .roomId(room.getRoomId())
                .build();
    }

    public LetterListData toLetterListData() {
        return LetterListData.builder()
                .createAt(getCreateAt())
                .roomId(room.getRoomId())
                .content(content)
                .senderId(sender.getId())
                .targetId(target.getId())
                .build();
    }
}
