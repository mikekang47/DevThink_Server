package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book save(Book book);
    Optional<Book> getBookByIsbn(int isbn);
}
