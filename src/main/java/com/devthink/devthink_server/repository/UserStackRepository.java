package com.devthink.devthink_server.repository;

import com.devthink.devthink_server.domain.UserStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStackRepository extends JpaRepository<UserStack, Long> {

    UserStack save(UserStack userStack);

}
