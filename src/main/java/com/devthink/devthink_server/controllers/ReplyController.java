package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CommentService;
import com.devthink.devthink_server.application.ReplyService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.Comment;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.ReplyRequestData;
import com.devthink.devthink_server.dto.ReplyResponseData;
import com.devthink.devthink_server.errors.ReplyBadRequestException;
import com.devthink.devthink_server.security.UserAuthentication;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;
    private final CommentService commentService;
    private final UserService userService;

    public ReplyController(ReplyService replyService,
                           CommentService commentService,
                           UserService userService) {
        this.replyService = replyService;
        this.commentService = commentService;
        this.userService = userService;
    }

    /**
     * 모든 Reply를 조회합니다.
     * @return DB 상에 존재하는 모든 Reply 리스트
     */
    @ApiOperation(value = "전체 대댓글 조회", notes = "모든 대댓글을 조회합니다.", response = List.class)
    @GetMapping
    @ApiIgnore
    public List<ReplyResponseData> getReplies() {
        return replyService.getReplies();
    }

    /**
     * 특정 사용자가 등록한 Reply를 모두 조회합니다.
     * @return 특정 사용자가 작성한 Reply 리스트
     */
    @ApiOperation(value = "사용자의 대댓글 조회",
            notes = "특정 사용자가 등록한 대댓글을 모두 조회합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = List.class)
    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public List<ReplyResponseData> getUserReplies(UserAuthentication userAuthentication) {
        Long userIdx = userAuthentication.getUserId();
        return replyService.getUserReplies(userIdx);
    }

    /**
     * 특정 Comment의 Reply를 조회합니다.
     * @param commentIdx 조회할 대상 댓글의 식별자
     * @return 특정 댓글에 작성된 Reply 리스트
     */
    @ApiOperation(value = "댓글 대댓글 조회", notes = "특정 댓글의 대댓글을 조회합니다.", response = List.class)
    @ApiImplicitParam(name = "commentIdx", value = "조회할 대상 댓글의 식별자")
    @GetMapping("/comment/{commentIdx}")
    public List<ReplyResponseData> getCommentReplies(@PathVariable("commentIdx") Long commentIdx) {
        return replyService.getCommentReplies(commentIdx);
    }

    /**
     * 입력된 reply 정보로 Comment에 등록할 새로운 Reply를 생성합니다.
     * @param replyRequestData 생성하려는 Reply의 요청 정보
     * @return 생성된 Reply
     */
    @ApiOperation(value = "대댓글 등록",
            notes = "입력된 대댓글 정보로 새로운 대댓글을 등록합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = String.class)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public ReplyResponseData createReply(@Valid @RequestBody ReplyRequestData replyRequestData,
                                         UserAuthentication userAuthentication){
        Long commentId = replyRequestData.getCommentId();
        // request상에 CommentId 값이 들어있는지 확인합니다.
        if (commentId != null) {
            // userId 값을 통하여 userRepository에서 User를 가져옵니다.
            User user = userService.getUser(userAuthentication.getUserId());
            // commentId 값을 통하여 commentRepository에서 Comment를 가져옵니다.
            Comment comment = commentService.getComment(commentId);
            return replyService.createReply(user, comment, replyRequestData.getContent());
        } else {
            throw new ReplyBadRequestException();
        }
    }

    /**
     * replyId를 통하여 기존의 Reply를 수정합니다.
     * @return Reply 수정 결과 response
     */
    @ApiOperation(value = "대댓글 수정",
            notes = "입력된 대댓글의 식별자로 수정할 대댓글을 찾아, 주어진 데이터로 대댓글의 정보를 갱신합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.",
            response = ReplyResponseData.class)
    @ApiImplicitParam(name = "replyId", value = "수정할 대댓글의 식별자")
    @PatchMapping("/{replyId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public ReplyResponseData updateReply(@PathVariable("replyId") Long replyId,
                                             @Valid @RequestBody ReplyRequestData replyRequestData){
        return replyService.updateReply(replyId, replyRequestData.getContent());
    }

    /**
     * replyId를 통하여 기존의 Reply를 삭제합니다.
     * @param replyId 삭제할 Reply의 식별자
     */
    @ApiOperation(value = "대댓글 삭제", notes = "입력된 대댓글의 식별자로 대댓글을 찾아 삭제합니다. 헤더에 사용자 토큰 주입을 필요로 합니다.")
    @ApiImplicitParam(name = "replyId", value = "삭제할 대댓글의 식별자")
    @DeleteMapping("/{replyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void deleteReply(@PathVariable("replyId") Long replyId) {
        replyService.deleteReply(replyId);
    }

}
