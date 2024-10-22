package com.example.filmorate.service;

import com.example.filmorate.dao.Film;
import com.example.filmorate.dto.FilmDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FilmService {
    FilmDto addNewFilm(FilmDto filmDto);

    FilmDto updateFilmById(FilmDto filmDto);

    List<FilmDto> getAllFilms(FilmDto filmDto);

    void addLike(Long id, Long userId);

    ResponseEntity<Void> removeLike(Long id, Long userId);

    List<Film> getTopFilms(Long count);
}