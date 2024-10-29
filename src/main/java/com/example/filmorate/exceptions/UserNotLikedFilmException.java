package com.example.filmorate.exceptions;

public class UserNotLikedFilmException extends RuntimeException {
    public UserNotLikedFilmException(String message) {
        super(message);
    }
}