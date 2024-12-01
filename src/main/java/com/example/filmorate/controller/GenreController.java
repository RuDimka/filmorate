package com.example.filmorate.controller;

import com.example.filmorate.entity.Genre;
import com.example.filmorate.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
@Slf4j
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/{id}")
    public Optional<Genre> getGenreById(@PathVariable Integer id) {
        log.info("Запрос на получение жанра по ID");
        return genreService.getGenreById(id);
    }

    @GetMapping
    public List<Genre> getListAllGenres() {
        log.info("Запрос на получение списка жанров");
        return genreService.getAllGenres();
    }
}