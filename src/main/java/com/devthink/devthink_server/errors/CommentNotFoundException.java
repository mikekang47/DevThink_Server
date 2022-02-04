package com.devthink.devthink_server.errors;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
        super("Comment not found");
    }

    public CommentNotFoundException(Long id) {
        super("Comment not found: " + id);
    }


}
