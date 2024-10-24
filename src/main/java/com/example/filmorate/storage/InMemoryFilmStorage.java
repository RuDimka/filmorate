package com.example.filmorate.storage;

import com.example.filmorate.dao.Film;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryFilmStorage implements FilmStorage {
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

    public List<Film> getAllFilm() {
        return new ArrayList<>(filmMap.values());
    }

    public Optional<Film> getFilmById(Long id) {
        return Optional.ofNullable(filmMap.get(id));
    }
}