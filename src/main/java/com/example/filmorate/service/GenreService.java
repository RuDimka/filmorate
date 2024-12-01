package com.example.filmorate.service;

import com.example.filmorate.entity.Genre;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GenreService {

    Optional<Genre> getGenreById(Integer genreId);

    List<Genre> getAllGenres();
}