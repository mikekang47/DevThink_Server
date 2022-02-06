package com.devthink.devthink_server.errors;

public class UserStackBadRequestException extends RuntimeException {
    public UserStackBadRequestException() {
        super("Can't create user stack with null stackId");
    }
}
