package com.devthink.devthink_server.repository;

import com.devthink.devthink_server.domain.UserStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStackRepository extends JpaRepository<UserStack, Long> {

    UserStack save(UserStack userStack);

    Optional<UserStack> findByUserId(Long userId);
}
