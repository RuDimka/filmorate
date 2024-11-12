package com.example.filmorate.service.impl;

import com.example.filmorate.entity.Genre;
import com.example.filmorate.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    @Override
    public Genre getGenreById(Integer genreId) {
        return null;
    }

    @Override
    public List<Genre> getAllGenres() {
        return List.of();
    }
}
