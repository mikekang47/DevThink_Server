package com.devthink.devthink_server.controllers;


import com.devthink.devthink_server.errors.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ControllerAdvice
public class ControllerErrorAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFound() {
        return new ErrorResponse("User not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserEmailDuplicationException.class)
    public ErrorResponse handleUserEmailIsAlreadyExists() {
        return new ErrorResponse("User's email already exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNickNameDuplicationException.class)
    public ErrorResponse handleUserNickNameIsAlreadyExists() {
        return new ErrorResponse("User's nickname already exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginFailException.class)
    public ErrorResponse handleLoginFailException() {
        return new ErrorResponse("Login fail");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidTokenException.class)
    public ErrorResponse handInvalidAccessTokenException() {
        return new ErrorResponse("Invalid access token");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ReviewNotFoundException.class)
    public ErrorResponse handleReviewNotFound() {
        return new ErrorResponse("Review not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyReviewedException.class)
    public ErrorResponse handleAlreadyReviewed() {
        return new ErrorResponse("User Already Reviewed");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public ErrorResponse handlePostNotFound() {
        return new ErrorResponse("Post not found");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StackNotFoundException.class)
    public ErrorResponse handleStackNotFound() {
        return new ErrorResponse("Stack not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserStackBadRequestException.class)
    public ErrorResponse handleStackBadRequest() {
        return new ErrorResponse("Stack id doesn't exits");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public ErrorResponse handleBookNotFound() {
        return new ErrorResponse("Book not found");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HeartNotFoundException.class)
    public ErrorResponse handleHeartNotFound() {
        return new ErrorResponse("Heart not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HeartAlreadyExistsException.class)
    public ErrorResponse handleHeartBadRequest() {
        return new ErrorResponse("Heart already exists.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ErrorResponse handleCategoryNotFound() {
        return new ErrorResponse("Category not found");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserRoomNotFoundException.class)
    public ErrorResponse handleUserRoomNotFound() {
        return new ErrorResponse("UserRoom not found");
    }
  
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CommentNotFoundException.class)
    public ErrorResponse handleCommentNotFound() {
        return new ErrorResponse("Comment not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ReviewCommentBadRequestException.class)
    public ErrorResponse handleReviewCommentBadRequest() {
        return new ErrorResponse("Can't create comment with null reviewId");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PostCommentBadRequestException.class)
    public ErrorResponse handlePostCommentBadRequest() {
        return new ErrorResponse("Can't create comment with null postId");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ReplyNotFoundException.class)
    public ErrorResponse handleReplyNotFound() {
        return new ErrorResponse("Reply not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ReplyBadRequestException.class)
    public ErrorResponse handleReplyBadRequest() {
        return new ErrorResponse("Can't create reply with null commentId");
    }


}

