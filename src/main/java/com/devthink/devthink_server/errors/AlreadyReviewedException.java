package com.devthink.devthink_server.errors;

public class AlreadyReviewedException extends RuntimeException {
    public AlreadyReviewedException(Long id) {
        super("User Already reviewed this Book: " + id);
    }
}