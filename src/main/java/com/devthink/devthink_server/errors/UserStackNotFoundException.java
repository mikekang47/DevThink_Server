package com.devthink.devthink_server.errors;

public class UserStackNotFoundException extends RuntimeException {
    public UserStackNotFoundException(Long id) {
        super("User stack not found: "+ id);
    }

}
