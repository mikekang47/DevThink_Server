package com.devthink.devthink_server.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class BookListResponseDto {

    private BookResponseDto bestBook;

    @Builder.Default
    private List<BookResponseDto> bookList = new ArrayList<>();

}
