package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Like save(Like like);

    void deleteById(Long id);
}
