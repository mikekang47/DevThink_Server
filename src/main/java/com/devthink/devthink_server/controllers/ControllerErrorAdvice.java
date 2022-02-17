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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PostUpdateBadRequestException.class)
    public ErrorResponse handlePostUpdateBadRequest() {
        return new ErrorResponse("Can't update Post with invalid userId");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotMatchException.class)
    public ErrorResponse handleUserNotMatch() {
        return new ErrorResponse("The user ID of the post doesn't match");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PostReportAlreadyRequestException.class)
    public ErrorResponse handlePostReportAlreadyRequest() {
        return new ErrorResponse("can't report the same post twice");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LetterUserNotFoundException.class)
    public ErrorResponse handleLetterUserNotFound() {
        return new ErrorResponse("The user of the nickname could not be found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PostReportBadRequestException.class)
    public ErrorResponse handlePostReportBadRequest() {
        return new ErrorResponse("can't report own posts");
    }

    @ExceptionHandler(PointNotValidException.class)
    public ErrorResponse handlePointNotValidException() {
        return new ErrorResponse("User can get 0 or 5 or 7 points from review");
    }

}

