package com.example.filmorate.service.impl;

import com.example.filmorate.entity.Genre;
import com.example.filmorate.exceptions.GenreIdNotFoundException;
import com.example.filmorate.service.GenreService;
import com.example.filmorate.storage.impl.GenreDbStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreDbStorage genreDbStorage;

    @Override
    public Genre getGenreById(Integer genreId) {
        if (genreId > 6) {
            throw new GenreIdNotFoundException("Genre id must be less than 6");
        }
        log.info("Жанр с ID {}", genreId);
        return genreDbStorage.getGenreById(genreId);
    }

    @Override
    public List<Genre> getAllGenres() {
        log.info("Список всех жанров");
        return genreDbStorage.getAllGenres();
    }
}