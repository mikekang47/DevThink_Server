package com.devthink.devthink_server.errors;

public class PostUpdateBadRequestException extends RuntimeException {
    public PostUpdateBadRequestException() { super("Can't update post with invalid userId!"); }
}
