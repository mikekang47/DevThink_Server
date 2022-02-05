package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CommentService;
import com.devthink.devthink_server.application.ReplyService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.dto.ReplyResponseData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
     * @param userIdx 대댓글을 조회할 사용자의 식별자
     * @return 특정 사용자가 작성한 Reply 리스트
     */
    @ApiOperation(value = "사용자의 대댓글 조회",
            notes = "특정 사용자가 등록한 대댓글을 모두 조회합니다.",
            response = List.class)
    @ApiImplicitParam(name = "userIdx", value = "대댓글을 조회할 사용자의 식별자")
    @GetMapping("/user/{userIdx}")
    public List<ReplyResponseData> getUserReplies(@PathVariable("userIdx") Long userIdx) {
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
    @ApiIgnore
    public List<ReplyResponseData> getCommentReplies(@PathVariable("commentIdx") Long commentIdx) {
        return replyService.getCommentReplies(commentIdx);
    }


}
