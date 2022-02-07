package com.devthink.devthink_server.errors;

public class StackNotFoundException extends RuntimeException {
    public StackNotFoundException(Long stackId) {
        super("Stack not found: "+ stackId);
    }
}
