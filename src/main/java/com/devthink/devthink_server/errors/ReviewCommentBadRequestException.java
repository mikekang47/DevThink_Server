package com.devthink.devthink_server.errors;

public class ReviewCommentBadRequestException extends RuntimeException {
    public ReviewCommentBadRequestException() {
        super("Can't create comment with null reviewId");
    }
}
