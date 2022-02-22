package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.LetterService;
import com.devthink.devthink_server.application.UserRoomService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.Letter;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.domain.UserRoom;
import com.devthink.devthink_server.dto.LetterListData;
import com.devthink.devthink_server.dto.LetterSendData;
import com.devthink.devthink_server.dto.LetterResultData;
import com.devthink.devthink_server.security.UserAuthentication;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class LetterController {

    private final LetterService letterService;
    private final UserService userService;
    private final UserRoomService userRoomService;

    /**
     * 닉네임을 통한 쪽지 전송 API
     * [POST] /messages
     * @param letterSendData 쪽지 데이터
     * @return LetterResultData 보낸 쪽지
     */
    @PostMapping
    @ApiOperation(value = "쪽지 전송", notes = "쪽지 정보를 받아 쪽지 리스트에서 쪽지를 보냅니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public LetterResultData createMessage(@RequestBody @Valid LetterSendData letterSendData,
                                          UserAuthentication userAuthentication
    ) throws AccessDeniedException {
        Long userId = userAuthentication.getUserId();
        User sender = userService.getUser(userId);
        // 상대방의 닉네임을 가져와서 상대방의 아이디를 가져옵니다.
        User target = letterService.findByNickname(letterSendData.getNickname());
        UserRoom userRoom = getUserRoom(letterSendData, sender, target);
        Letter letter = letterService.createMessage(userRoom, sender, target, letterSendData);
        return letter.toLetterResultData();
    }

    /**
     * 쪽지 리스트 API
     * [GET] /messages/lists?page= &size= &sort= ,정렬방식
     * @Param pageable 페이지 정렬 방식
     * @return List<LetterResultData> 쪽지 리스트
     */
    @GetMapping("/lists")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "쪽지 리스트", notes = "유저 Id를 받아 쪽지 리스트를 반환합니다.")
    public List<LetterListData> messageList(UserAuthentication userAuthentication) throws AccessDeniedException {
        Long userId = userAuthentication.getUserId();
        User user = userService.getUser(userId);
        return letterService.getMessageList(user);
    }

    /**
     * 쪽지 읽기 API
     * [GET] /messages/lists/rooms/:roomId
     * @param roomId 방 아이디
     * @return List<LetterResultData> 읽은 쪽지
     */
    @GetMapping("/lists/rooms/{roomId}")
    @ApiOperation(value = "쪽지 내용 가져오기", notes = "방 id를 받아서 받은 쪽지를 읽습니다.")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public List<LetterResultData> getMessage(@PathVariable("roomId") Long roomId,
                                             UserAuthentication userAuthentication
    ) throws AccessDeniedException {
        Long userId = userAuthentication.getUserId();
        User user = userService.getUser(userId);
        UserRoom userRoom = userRoomService.getUserRoom(roomId);
        return letterService.getMessage(user, userRoom);
    }

    /**
     * 쪽지를 보낼때, 유저의 방을 설정합니다.
     * @param letterSendData 쪽지 데이터
     * @param sender 쪽지 보낸 사람 데이터
     * @param target 쪽지 받는 사람 데이터
     * @return UserRoom 유저의 방 데이터
     */
    private UserRoom getUserRoom(LetterSendData letterSendData, User sender, User target) {
        UserRoom userRoom;
        if (letterSendData.getRoomId() == 0) {
            int existRoom = userRoomService.existChat(sender.getId(), target.getId());
            if (existRoom == 0) {
                Long maxRoom = userRoomService.getMaxRoom();
                if (maxRoom == null)
                    userRoom = userRoomService.save(sender, target, 1L);
                else
                    userRoom = userRoomService.save(sender, target, maxRoom + 1);
            } else {
                userRoom = userRoomService.getExistUserRoom(sender.getId(), target.getId());
            }
        } else {
            userRoom = userRoomService.getUserRoom(sender.getId(), target.getId(), letterSendData.getRoomId());
        }
        return userRoom;
    }

}
