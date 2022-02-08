package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.UserStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStackRepository extends JpaRepository<UserStack, Long> {

    UserStack save(UserStack userStack);

    List<UserStack> findAllByUserId(Long userId);

    Optional<UserStack> findById(Long stackId);
}
