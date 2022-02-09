package com.devthink.devthink_server.errors;

public class ReplyBadRequestException extends RuntimeException {
    public ReplyBadRequestException() {
        super("Can't create reply with null commentId");
    }
}
