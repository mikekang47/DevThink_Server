package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface LetterRepository extends JpaRepository<Letter, Long> {

    //메시지 리스트 가져오기
    @Query("select u from Letter u where u.id " +
            "in (select max(u.id) from Letter group by u.roomId) " +
            "and (u.senderId = :userId or u.targetId = :userId) order by u.id desc")
    ArrayList<Letter> getMessageList(Long userId);


    // 안읽은 메시지 갯수 가져오기
    @Query("select count(u.id) from Letter u where u.targetId = :userId and u.readCheck = 0 and u.roomId = :roomId")
    Long countUnread(Long userId, Long roomId);


}
