package com.example.filmorate.exceptions;

public class GenreIdNotFoundException extends RuntimeException {
    public GenreIdNotFoundException(String message) {
        super(message);
    }
}