package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
    @Query("select case when count(p) > 0 then true else false end from PostReport p" +
            " where p.user.id = :userId and p.post.id = :postId")
    boolean existsPostReport(Long userId, Long postId);

}
