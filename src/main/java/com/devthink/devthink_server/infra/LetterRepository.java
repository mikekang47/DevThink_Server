package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Letter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.ManyToOne;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {

    // 방 별 메시지 리스트 가져오기
    @Query("select u from Letter u where u.id " +
            "in (select max(p.id) from Letter p group by p.room.roomId) " +
            "and (u.sender.id = :userId or u.target.id = :userId)")
    Page<Letter> getMessageList(Long userId, Pageable pageable);

    // 안읽은 메시지 개수 가져오기
    @Query("select count(u.id) from Letter u where u.target.id = :userId and u.readCheck = false and u.room.roomId=:roomId")
    Long countUnread(Long userId, Long roomId);

    // 메시지 내용 가져오기
    @Query("select u from Letter u where u.room.roomId = :roomId and " +
            "(u.sender.id = :userId or u.target.id = :userId)")
    List<Letter> getRoomLists(Long userId, Long roomId);


    @Query("select u from Letter u where u.room.roomId = :roomId and " +
            "u.target.id = :userId and u.readCheck = false")
    List<Letter> getUnReadLists(Long userId, Long roomId);
}
