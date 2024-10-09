package com.example.filmorate.service;

import com.example.filmorate.dto.FilmDto;
import org.springframework.stereotype.Service;

@Service
public interface FilmService {
    FilmDto addNewFilm(FilmDto filmDto);
}
