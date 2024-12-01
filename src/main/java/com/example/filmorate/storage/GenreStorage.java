package com.example.filmorate.storage;

import com.example.filmorate.entity.Genre;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreStorage {

    Optional<Genre> getGenreById(int genreId);

    List<Genre> getFilmGenres(long filmId);

    List<Genre> getAllGenres();
}