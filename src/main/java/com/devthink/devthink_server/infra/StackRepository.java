package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Stack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {
    Optional<Stack> findById(Long id);
}
