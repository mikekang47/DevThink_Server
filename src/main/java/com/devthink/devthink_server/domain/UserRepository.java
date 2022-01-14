package com.devthink.devthink_server.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findById(Long id);

    Optional<User> findByIdAndDeletedIsFalse(Long id);
}
