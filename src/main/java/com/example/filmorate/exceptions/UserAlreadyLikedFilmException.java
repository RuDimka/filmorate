package com.example.filmorate.exceptions;

public class UserAlreadyLikedFilmException extends RuntimeException{
    public UserAlreadyLikedFilmException(String message){
        super(message);
    }
}
