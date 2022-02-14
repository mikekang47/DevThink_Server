package com.devthink.devthink_server.errors;

public class PostReportBadRequestException extends RuntimeException {
    public PostReportBadRequestException() { super("Can't report own posts!"); }
}
