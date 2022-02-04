package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Heart, Long> {

    Heart save(Heart heart);

    void deleteById(Long id);
}
