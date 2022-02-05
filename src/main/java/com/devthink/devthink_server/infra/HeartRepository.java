package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

    Heart save(Heart heart);

    void deleteById(Long id);

}
