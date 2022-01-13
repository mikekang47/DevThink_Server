package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.infra.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    //책 등록
    public Book createBook(int isbn){
        Book book = Book.builder().isbn(isbn).build();
        return bookRepository.save(book);
    }


}
