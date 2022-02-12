package com.devthink.devthink_server.errors;

public class PointNotValidException extends RuntimeException {
    public PointNotValidException() { super("User can get 0 or 5 or 7 point from review"); }
}
