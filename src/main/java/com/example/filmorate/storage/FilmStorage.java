package com.example.filmorate.storage;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.entity.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film saveFilm(FilmDto filmDto);

    Film updateFilm(FilmDto filmDto);

    List<Film> getAllFilm();

    Optional<Film> getFilmById(Long id);

    void addLike(Long userId);

    void removeLike(Long userId);

    int getLikeCount();
}