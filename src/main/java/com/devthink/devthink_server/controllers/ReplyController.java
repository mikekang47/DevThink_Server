package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CommentService;
import com.devthink.devthink_server.application.ReplyService;
import com.devthink.devthink_server.application.UserService;
import org.springframework.web.bind.annotation.*;

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

}
