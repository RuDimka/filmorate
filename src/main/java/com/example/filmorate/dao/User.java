package com.example.filmorate.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String login;
    private String name;
    private String birthday;
}