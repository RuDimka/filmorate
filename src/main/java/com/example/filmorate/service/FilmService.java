package com.example.filmorate.service;

import com.example.filmorate.dto.FilmDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FilmService {
    FilmDto addNewFilm(FilmDto filmDto);

    FilmDto updateFilmById(FilmDto filmDto);

    List<FilmDto> getAllFilms(FilmDto filmDto);
}
