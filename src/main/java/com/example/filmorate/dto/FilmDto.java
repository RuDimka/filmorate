package com.example.filmorate.dto;

import lombok.Data;

@Data
public class FilmDto {

    private String name;
    private String description;
    private String releaseDate;
    private int duration;
}
