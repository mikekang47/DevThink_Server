package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    
    Optional<User> findById(Long id);

    Optional<User> findByIdAndDeletedIsFalse(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String userNickname);
    
    boolean existsByEmail(String existed_email);

    boolean existsByNickname(String userNickName);

    String findImageUrlById(Long targetId);
    
    String findNicknameById(Long id);

}
