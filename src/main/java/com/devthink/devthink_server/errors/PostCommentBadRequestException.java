package com.devthink.devthink_server.errors;

public class PostCommentBadRequestException extends RuntimeException {
    public PostCommentBadRequestException() {
        super("Can't create comment with null postId");
    }
}
