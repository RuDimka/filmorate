package com.example.filmorate.dao;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class FilmStorageDao {
    private final Map<Long, Film> filmMap = new HashMap<>();
    private final AtomicLong filmIdGenerator = new AtomicLong(1);

    public Film saveFilm(Film film) {
        film.setId(filmIdGenerator.getAndIncrement());
        filmMap.put(film.getId(), film);
        return film;
    }

    public Film updateFilm(Film film) {
        return filmMap.put(film.getId(), film);
    }

    public List<Film> getAllFilm(Film film) {
        return new ArrayList<>(filmMap.values());
    }

    public Optional<Film> getFilmById(Long id) {
        return Optional.ofNullable(filmMap.get(id));
    }
}