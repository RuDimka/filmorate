package com.example.filmorate.exceptions;

public class FriendAlreadyExistsException extends RuntimeException{
    public FriendAlreadyExistsException(String message) {
        super(message);
    }
}
