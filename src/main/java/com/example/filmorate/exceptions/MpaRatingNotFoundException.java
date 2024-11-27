package com.example.filmorate.exceptions;

public class MpaRatingNotFoundException extends RuntimeException {
    public MpaRatingNotFoundException(String message) {
        super(message);
    }
}