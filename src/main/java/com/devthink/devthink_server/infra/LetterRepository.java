package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {
}
