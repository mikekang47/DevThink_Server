package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.UserRoom;
import com.devthink.devthink_server.errors.UserRoomNotFoundException;
import com.devthink.devthink_server.infra.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRoomService {

    private final UserRoomRepository userRoomRepository;

    public UserRoom getUserRoom(Long senderId, Long targetId, Long roomId) {
        return userRoomRepository.getUserRoom(senderId, targetId, roomId)
                .orElseThrow(()-> new UserRoomNotFoundException(roomId));
    }
    public UserRoom getUserRoom(Long roomId) {
        return userRoomRepository.findByRoomIdAndDeletedIsFalse(roomId)
                .orElseThrow(()-> new UserRoomNotFoundException(roomId));
    }

    public UserRoom save(User user1, User user2, Long roomId) {
        UserRoom userRoom = userRoomRepository.save(
                UserRoom.builder()
                        .user1(user1)
                        .user2(user2)
                        .roomId(roomId)
                        .build()
        );
        return userRoom;
    }

    // 유저 방 id 가져오기
    public int existChat(Long senderId, Long targetId){
        return userRoomRepository.existChat(senderId, targetId);
    }

    // 유저 최대 방 id 가져오기
    public Long getMaxRoom(){
        return userRoomRepository.maxRoom();
    }

    public UserRoom getExistUserRoom(Long senderId, Long targetId){
        return userRoomRepository.selectRoom(senderId, targetId, PageRequest.of(0, 1)).get(0);
    }
}
