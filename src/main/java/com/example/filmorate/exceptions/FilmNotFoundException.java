package com.example.filmorate.exceptions;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(String message) {
        super(message);
    }
}