package com.example.filmorate.service.impl;

import com.example.filmorate.dao.Film;
import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final Film film;


    @Override
    public FilmDto addNewFilm(FilmDto filmDto) {
        return null;
    }
}
