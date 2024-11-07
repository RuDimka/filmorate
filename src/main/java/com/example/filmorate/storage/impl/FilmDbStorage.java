package com.example.filmorate.storage.impl;

import com.example.filmorate.entity.Film;
import com.example.filmorate.storage.FilmStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film saveFilm(Film film) {
        String sqlQuery = "INSERT INTO films (name, description, releaseDate, duration) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public List<Film> getAllFilm() {
        return List.of();
    }

    @Override
    public Optional<Film> getFilmById(Long id) {
        return Optional.empty();
    }

    @Override
    public void addLike(Long userId) {

    }

    @Override
    public void removeLike(Long userId) {

    }

    @Override
    public int getLikeCount() {
        return 0;
    }
}
