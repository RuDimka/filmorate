package com.example.filmorate.controller.advice;

import com.example.filmorate.dao.ErrorResponse;
import com.example.filmorate.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.info("Ошибка валидации");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.info("Ошибка, пользователь не существует");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(FilmNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerFilmNotFoundException(FilmNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.info("Ошибка, фильм не существует");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(FriendNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerFriendNotFoundException(FriendNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.info("Ошибка, друг не существует");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserNotLikedFilmException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotLikedFilmException(UserNotLikedFilmException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.info("Ошибка, лайк не добавлен");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyLikedFilmException.class)
    public ResponseEntity<String> handlerUserAlreadyLikedFilmException(UserAlreadyLikedFilmException ex) {
        log.info("Ошибка, лайк уже добавлен");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(FriendAlreadyExistsException.class)
    public ResponseEntity<String> handlerFriendAlreadyExistsException(FriendAlreadyExistsException ex) {
        log.info("Ошибка, пользователь уже добавлен в друзья");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}