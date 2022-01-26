package com.devthink.devthink_server.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookRequestDto {

    private Integer isbn;

    private String name;

    private String writer;

    private String imgUrl;

}
