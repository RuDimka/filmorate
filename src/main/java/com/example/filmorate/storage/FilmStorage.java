package com.example.filmorate.storage;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.entity.Film;
import com.example.filmorate.entity.Genre;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Film saveFilm(FilmDto filmDto) throws SQLException;

    Film updateFilm(FilmDto filmDto) throws SQLException;

    List<Film> getAllFilm();

    void addLike(Long filmId, Long userId);

    List<Genre> findByGenresByFilmId(Long filmId);

    void removeLikes(long filmId, long userId);

    Optional<List<Film>> getFilmTop(int count);

    Optional<Film> findById(Long id);
}