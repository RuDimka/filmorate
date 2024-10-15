package com.example.filmorate.service.impl.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
}