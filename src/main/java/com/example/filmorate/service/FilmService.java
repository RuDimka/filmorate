package com.example.filmorate.service;

import com.example.filmorate.entity.Film;
import com.example.filmorate.dto.FilmDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FilmService {
    FilmDto addNewFilm(FilmDto filmDto);

    FilmDto updateFilmById(FilmDto filmDto);

    List<FilmDto> getAllFilms(FilmDto filmDto);

    void addLike(Long id, Long userId);

    void removeLike(Long id, Long userId);

    List<Film> getTopFilms(Optional<Integer> count);
}