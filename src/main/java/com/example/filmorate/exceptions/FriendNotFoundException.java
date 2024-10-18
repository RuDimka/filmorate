package com.example.filmorate.exceptions;

public class FriendNotFoundException extends RuntimeException{
    public FriendNotFoundException(String message){
        super(message);
    }
}
