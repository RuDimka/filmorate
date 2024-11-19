package com.example.filmorate.service.impl;

import com.example.filmorate.entity.Genre;
import com.example.filmorate.service.GenreService;
import com.example.filmorate.storage.impl.GenreDbStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDbStorage genreDbStorage;

    @Override
    public Genre getGenreById(Integer genreId) {
        return genreDbStorage.getGenreById(genreId);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDbStorage.getAllGenres();
    }
}