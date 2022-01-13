package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.infra.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    //책 등록
    public Book createBook(int isbn){
        Book book = Book.builder().isbn(isbn).build();
        return bookRepository.save(book);
    }

    //isbn으로 책 조회, 없으면 생성
    public Book getBookByIsbn(int isbn){
        Optional<Book> book = bookRepository.getBookByIsbn(isbn);
        if (book.isEmpty()) {
            return createBook(isbn);
        } else {
            return book.get();
        }
    }

}
