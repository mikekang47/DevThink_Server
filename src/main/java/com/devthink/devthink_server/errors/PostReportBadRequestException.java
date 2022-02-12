package com.devthink.devthink_server.errors;

public class PostReportBadRequestException extends RuntimeException {
    public PostReportBadRequestException() { super("can't report post with invalid approach!"); }
}
