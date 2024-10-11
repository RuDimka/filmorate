package com.example.filmorate.dao;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Data
@Repository
public class User {
    private Long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
}