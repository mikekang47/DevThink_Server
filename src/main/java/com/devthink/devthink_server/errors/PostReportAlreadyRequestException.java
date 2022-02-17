package com.devthink.devthink_server.errors;

public class PostReportAlreadyRequestException extends RuntimeException {
    public PostReportAlreadyRequestException() { super("can't report the same post twice!"); }
}
