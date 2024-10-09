package com.example.filmorate.dao;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class Film {
    private Long id;
    private String name;
    private String description;
    private String releaseDate;
    private int duration;
}