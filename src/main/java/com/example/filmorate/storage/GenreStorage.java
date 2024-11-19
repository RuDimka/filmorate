package com.example.filmorate.storage;

import com.example.filmorate.entity.Genre;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreStorage {

    Genre getGenreById(int genreId);

    List<Genre> getFilmGenres(long filmId);

    List<Genre> getAllGenres();
}
