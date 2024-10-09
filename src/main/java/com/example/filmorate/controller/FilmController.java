package com.example.filmorate.controller;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public FilmDto addFilm(@RequestBody FilmDto filmDto) {
        return filmService.addNewFilm(filmDto);
    }
}
