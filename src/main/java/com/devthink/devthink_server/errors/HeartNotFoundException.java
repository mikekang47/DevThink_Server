package com.devthink.devthink_server.errors;

public class HeartNotFoundException extends RuntimeException {
    public HeartNotFoundException(Long id) {
        super("Heart not found: "+id);
    }

    public HeartNotFoundException() {
        super("Heart not found");
    }
}
