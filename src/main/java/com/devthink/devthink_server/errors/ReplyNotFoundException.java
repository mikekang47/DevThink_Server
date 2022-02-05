package com.devthink.devthink_server.errors;

public class ReplyNotFoundException extends RuntimeException {

    public ReplyNotFoundException(Long id) {
        super("Reply not found: " + id);
    }
}
