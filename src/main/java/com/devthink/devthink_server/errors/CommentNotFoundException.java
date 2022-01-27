package com.devthink.devthink_server.errors;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long id) {
        super("Comment not found: " + id);
    }
}
