package com.devthink.devthink_server.errors;

public class CommentContentEmptyException extends RuntimeException {
    public CommentContentEmptyException() {
        super("Comment content cannot be empty");
    }
}
