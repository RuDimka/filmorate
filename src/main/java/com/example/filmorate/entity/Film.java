package com.example.filmorate.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Data
@Repository
public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private int rate;
    private MpaRating mpa;
    private List<Genre> genres;
    private Long likes;
}