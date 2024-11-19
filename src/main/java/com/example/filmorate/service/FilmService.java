package com.example.filmorate.service;

import com.example.filmorate.entity.Film;
import com.example.filmorate.dto.FilmDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FilmService {
    Film addNewFilm(FilmDto filmDto);

    Film updateFilmById(FilmDto filmDto);

    List<FilmDto> getAllFilms(FilmDto filmDto);

    void addLike(long id, long userId);

    void removeLike(long id, long userId);

    List<Film> getTopFilms(Integer count);
}