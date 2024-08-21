package com.wesley.blog.controller;

import com.wesley.blog.exception.EntityNotFoundException;
import com.wesley.blog.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.lang.IllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage constraintViolationException(ConstraintViolationException ex, HttpServletRequest request){
        return new ErrorMessage(
                "ConstraintViolationException",
                ex.getMessage(),
                LocalDateTime.now()
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){
        return new ErrorMessage(
                "MethodArgumentNotValidException",
                ex.getMessage(),
                LocalDateTime.now()
        );
    }
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage entityNotFoundException(EntityNotFoundException ex, HttpServletRequest request){
        return new ErrorMessage(
                "EntityNotFoundException",
                ex.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage authenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex, HttpServletRequest request){
        return new ErrorMessage(
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                LocalDateTime.now()
        );
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage authorizationDeniedException(AuthorizationDeniedException ex, HttpServletRequest request){
        return new ErrorMessage(
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                LocalDateTime.now()
        );
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage genericError(Exception ex, HttpServletRequest request){
        return new ErrorMessage(
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                LocalDateTime.now()
        );
    }
}
