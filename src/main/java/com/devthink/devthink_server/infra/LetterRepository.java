package com.devthink.devthink_server.infra;

import com.devthink.devthink_server.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface LetterRepository extends JpaRepository<Letter, Long> {

    // 방 별 메시지 리스트 가져오기
    @Query("select u from Letter u where u.id " +
            "in (select max(u.id) from Letter group by u.roomId) " +
            "and (u.senderId = :userId or u.targetId = :userId) order by u.id desc")
    ArrayList<Letter> getMessageList(Long userId);

    // 안 읽은 메시지 갯수 가져오기
    @Query("select count(u.id) from Letter u where u.targetId = :userId and u.readCheck = 0 and u.roomId = :roomId")
    Long countUnread(Long userId, Long roomId);

    // 메시지 내용 가져오기
    @Query("select u from Letter u where u.roomId = :roomId and " +
            "(u.senderId = :userId or u.targetId = :userId)")
    ArrayList<Letter> getRoomLists(Long userId, Long roomId);

    // 메시지 읽음 처리하기
    @Modifying
    @Query("update Letter u set u.readCheck = 1 where u.roomId = :roomid and " +
            "u.readCheck = 0 and u.targetId = :userId")
    int MessageReadCheck(Long userId, Long roomId);

}
