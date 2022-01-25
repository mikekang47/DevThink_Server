package com.devthink.devthink_server.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookListResponseDto {

    private BookResponseDto bestBook;

    private List<BookResponseDto> bookList;

}
