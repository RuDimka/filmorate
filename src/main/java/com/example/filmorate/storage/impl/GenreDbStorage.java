package com.example.filmorate.storage.impl;

import com.example.filmorate.constant.SqlRequestConstant;
import com.example.filmorate.entity.Genre;
import com.example.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Genre> getGenreById(int genreId) {
        List<Genre> genres = jdbcTemplate.query(SqlRequestConstant.SQL_QUERY_GET_GENRE_BY_ID, new BeanPropertyRowMapper<>(Genre.class), genreId);
        return genres.stream().findFirst();
    }

    @Override
    public List<Genre> getFilmGenres(long filmId) {
        return jdbcTemplate.query(SqlRequestConstant.SQL_QUERY_GET_FILM_BY_GENRE, new BeanPropertyRowMapper<>(Genre.class), filmId);
    }

    @Override
    public List<Genre> getAllGenres() {
        return jdbcTemplate.query(SqlRequestConstant.SQL_QUERY_GET_ALL_GENRES, new BeanPropertyRowMapper<>(Genre.class));
    }
}