package com.behabits.gymbo.infrastructure.controller.http_error;

import com.behabits.gymbo.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NotFoundException.class
    })
    @ResponseBody
    public ErrorMessage notFoundRequest(Exception exception) {
        return new ErrorMessage(exception, HttpStatus.NOT_FOUND.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            org.springframework.web.HttpRequestMethodNotSupportedException.class,
            org.springframework.web.bind.MethodArgumentNotValidException.class,
            org.springframework.http.converter.HttpMessageNotReadableException.class,
            org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
            IncorrectFileException.class,
            SameReviewerException.class,
            IncorrectLinkException.class,
            SameReviewedException.class
    })
    @ResponseBody
    public ErrorMessage badRequest(Exception exception) {
        return new ErrorMessage(exception, HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({
            PermissionsException.class,
            BadCredentialsException.class
    })
    @ResponseBody
    public ErrorMessage forbiddenRequest(Exception exception) {
        return new ErrorMessage(exception, HttpStatus.FORBIDDEN.value());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            ExistingUserException.class
    })
    @ResponseBody
    public ErrorMessage conflictRequest(Exception exception) {
        return new ErrorMessage(exception, HttpStatus.CONFLICT.value());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            Exception.class
    })
    @ResponseBody
    public ErrorMessage exception(Exception exception) {
        return new ErrorMessage(exception, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
