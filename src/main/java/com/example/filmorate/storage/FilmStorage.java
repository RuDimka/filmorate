package com.example.filmorate.storage;

import com.example.filmorate.entity.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film saveFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getAllFilm();

    Optional<Film> getFilmById(Long id);

    void addLike(Long userId);

    void removeLike(Long userId);

    int getLikeCount();
}