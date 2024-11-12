package com.example.filmorate.dto;

import com.example.filmorate.entity.Genre;
import com.example.filmorate.entity.MpaRating;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FilmDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private int rate;
    private MpaRating mpa;
    private List<Genre> genreList;
}