package com.example.filmorate.dto;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
}
