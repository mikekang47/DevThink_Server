package com.devthink.devthink_server.errors;

public class CommentBadRequestException extends RuntimeException {
    public CommentBadRequestException() {
        super("Can't create comment on Review and Post at the same time, or both null");
    }
}
