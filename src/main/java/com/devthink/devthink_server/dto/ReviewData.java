package com.devthink.devthink_server.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewData {
    Long id;
    String user_id;
    String book_id;
    String content;
    Float Score;
}
