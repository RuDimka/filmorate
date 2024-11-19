package com.example.filmorate.storage;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.entity.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Film saveFilm(FilmDto filmDto);

    Film updateFilm(FilmDto filmDto);

    List<Film> getAllFilm();

    void addLike(long filmId, long userId);

    void removeLike(long filmId, long userId);
}