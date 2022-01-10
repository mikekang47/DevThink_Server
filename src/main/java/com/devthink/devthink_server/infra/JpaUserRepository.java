package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends UserRepository, JpaRepository<Long, User> {

    User save(User user);

}
