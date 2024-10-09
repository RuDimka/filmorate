package com.example.filmorate.dao;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
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
}