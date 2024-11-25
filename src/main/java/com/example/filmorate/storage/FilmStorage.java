package com.example.filmorate.storage;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.entity.Film;
import com.example.filmorate.entity.Genre;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface FilmStorage {

    Film saveFilm(FilmDto filmDto) throws SQLException;

    Film updateFilm(FilmDto filmDto) throws SQLException;

    List<Film> getAllFilm();

    void saveLike(Film film);

    void loadLikes(Film film);

    void removeLikes(long filmId, long userId);

    List<Film> getFilmTop(int count);

    Film findById(Long id);

    Set<Genre> getGenresByFilm(Film film);
}