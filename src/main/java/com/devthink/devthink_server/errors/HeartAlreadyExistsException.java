package com.devthink.devthink_server.errors;

public class HeartAlreadyExistsException extends RuntimeException {
    public HeartAlreadyExistsException() {
        super("Heart Already exists.");
    }
}
