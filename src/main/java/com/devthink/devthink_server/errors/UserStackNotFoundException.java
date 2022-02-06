package com.devthink.devthink_server.errors;

public class UserStackNotFoundException extends RuntimeException {
    public UserStackNotFoundException() {
        super("User doesn't have any stack");
    }

}
