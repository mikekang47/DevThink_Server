package com.devthink.devthink_server.errors;

public class LikeNotFoundException extends RuntimeException {
    public LikeNotFoundException(Long id) {
        super("Like Not found: "+id);
    }
}
