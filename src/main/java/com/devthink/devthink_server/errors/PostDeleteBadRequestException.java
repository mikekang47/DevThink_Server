package com.devthink.devthink_server.errors;

public class PostDeleteBadRequestException extends RuntimeException {
    public PostDeleteBadRequestException() {  super("Can't delete post with invalid userId!"); }
}
