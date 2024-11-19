package com.example.filmorate.controller.advice;

import com.example.filmorate.entity.ErrorResponse;
import com.example.filmorate.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.info("Ошибка валидации");
        return errorResponse;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.info("Ошибка, пользователь не существует");
        return errorResponse;
    }

    @ExceptionHandler(FilmNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerFilmNotFoundException(FilmNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.info("Ошибка, фильм не существует");
        return errorResponse;
    }

    @ExceptionHandler(FriendNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerFriendNotFoundException(FriendNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.info("Ошибка, друг не существует");
        return errorResponse;
    }

    @ExceptionHandler(UserNotLikedFilmException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerUserNotLikedFilmException(UserNotLikedFilmException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.info("Ошибка, лайк не добавлен");
        return errorResponse;
    }

    @ExceptionHandler(UserAlreadyLikedFilmException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerUserAlreadyLikedFilmException(UserAlreadyLikedFilmException ex) {
        log.info("Ошибка, лайк уже добавлен");
        return ex.getMessage();
    }

    @ExceptionHandler(FriendAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handlerFriendAlreadyExistsException(FriendAlreadyExistsException ex) {
        log.info("Ошибка, пользователь уже добавлен в друзья");
        return ex.getMessage();
    }

    @ExceptionHandler(UserRemoveException.class)
    @ResponseStatus(HttpStatus.OK)
    public String handlerUserRemoveException(UserRemoveException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(GenreNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerGenreNotFoundException(GenreNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(MpaRatingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerMpaRatingNotFoundException(MpaRatingNotFoundException ex) {
        return ex.getMessage();
    }
}