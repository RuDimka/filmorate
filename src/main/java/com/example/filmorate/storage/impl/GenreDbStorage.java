package com.example.filmorate.storage.impl;

import com.example.filmorate.entity.Genre;
import com.example.filmorate.exceptions.GenreNotFoundException;
import com.example.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre getGenreById(int genreId) {
        String sql = "SELECT * FROM genres WHERE id = ?";
        List<Genre> genres = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Genre.class), genreId);
        if (genres.isEmpty()) {
            throw new GenreNotFoundException("Жанр с id: " + genreId + " не найден");
        }
        return genres.get(0);
    }

    @Override
    public List<Genre> getFilmGenres(long filmId) {
        String sql = "SELECT * FROM genres WHERE id IN (SELECT id FROM film_genres WHERE film_id = ? ORDER BY id)";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Genre.class), filmId);
    }

    @Override
    public List<Genre> getAllGenres() {
        String sql = "SELECT * FROM genres ORDER BY id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Genre.class));
    }
}