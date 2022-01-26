package com.devthink.devthink_server.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookDetailResponseDto {

    BookResponseDto book;

    List<ReviewResponseDto> reviews;

}
