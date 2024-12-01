package com.example.filmorate.service;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.entity.Film;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public interface FilmService {

    Film addNewFilm(FilmDto filmDto) throws SQLException;

    Film updateFilmById(FilmDto filmDto) throws SQLException;

    List<Film> getAllFilms();

    void removeLike(long id, long userId);

    Optional<List<Film>> getTopFilms(int count);

    void addLike(Long id, Long userId);

    Optional<Film> getFilmById(Long id);
}