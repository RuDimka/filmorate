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
        log.warn("Ошибка валидации");
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerUserNotFoundException(UserNotFoundException ex) {
        log.warn("Ошибка, пользователь не существует");
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(FilmNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerFilmNotFoundException(FilmNotFoundException ex) {
        log.warn("Ошибка, фильм не существует");
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(FriendNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerFriendNotFoundException(FriendNotFoundException ex) {
        log.warn("Ошибка, друг не существует");
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UserNotLikedFilmException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerUserNotLikedFilmException(UserNotLikedFilmException ex) {
        log.warn("Ошибка, лайк не добавлен");
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyLikedFilmException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerUserAlreadyLikedFilmException(UserAlreadyLikedFilmException ex) {
        log.warn("Ошибка, лайк уже добавлен");
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(FriendAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerFriendAlreadyExistsException(FriendAlreadyExistsException ex) {
        log.warn("Ошибка, пользователь уже добавлен в друзья");
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UserRemoveException.class)
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponse handlerUserRemoveException(UserRemoveException ex) {
        log.warn("Такой пользователь не найден");
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(GenreNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerGenreNotFoundException(GenreNotFoundException ex) {
        log.warn("Ошибка, жанр не найден");
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(MpaRatingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerMpaRatingNotFoundException(MpaRatingNotFoundException ex) {
        log.warn("Ошибка, рейтинг не существует");
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(MpaIdNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerMpaIdNotFoundException(MpaIdNotFoundException ex) {
        log.warn("Ошибка, рейтинга c таким id нет в базе данных");
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(GenreIdNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerGenreIdNotFoundException(GenreIdNotFoundException ex) {
        log.warn("Ошибка, жанра c таким id нет в базе данных");
        return new ErrorResponse(ex.getMessage());
    }
}