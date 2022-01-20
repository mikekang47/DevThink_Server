package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.errors.ErrorResponse;
import com.devthink.devthink_server.errors.ReviewNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@RestControllerAdvice
public class ControllerErrorAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ReviewNotFoundException.class)
    public ErrorResponse handleUserNotFound() {
        return new ErrorResponse("Review not found");
    }
}