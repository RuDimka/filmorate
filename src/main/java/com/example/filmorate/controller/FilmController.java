package com.example.filmorate.controller;

import com.example.filmorate.dao.Film;
import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public FilmDto addFilm(@RequestBody FilmDto filmDto) {
        log.info("Получен запрос на добавление фильма");
        return filmService.addNewFilm(filmDto);
    }

    @PutMapping()
    public FilmDto updateFilmById(@RequestBody FilmDto filmDto) {
        log.info("Получен запрос на редактирование фильма {} ", filmDto.getName());
        return filmService.updateFilmById(filmDto);
    }

    @GetMapping
    public List<FilmDto> getAllFilms(FilmDto filmDto) {
        log.info("Получен запрос на список фильмов");
        return filmService.getAllFilms(filmDto);
    }

    @PutMapping("{id}/like/{userId}")
    public void addLike(@PathVariable Long id,
                        @PathVariable Long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void removeLike(@PathVariable Long id,
                           @PathVariable Long userId) {
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam Optional<Integer> count) {
        return filmService.getTopFilms(count);
    }
}