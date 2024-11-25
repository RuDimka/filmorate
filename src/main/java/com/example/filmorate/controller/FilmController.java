package com.example.filmorate.controller;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.entity.Film;
import com.example.filmorate.exceptions.MpaRatingNotFoundException;
import com.example.filmorate.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film addFilm(@RequestBody FilmDto filmDto) throws SQLException, MpaRatingNotFoundException {
        log.info("Получен запрос на добавление фильма");
        return filmService.addNewFilm(filmDto);
    }

    @PutMapping()
    public Film updateFilmById(@RequestBody FilmDto filmDto) throws SQLException, MpaRatingNotFoundException {
        log.info("Получен запрос на редактирование фильма {} ", filmDto.getName());
        return filmService.updateFilmById(filmDto);
    }

    @GetMapping
    public List<Film> getAllFilms() throws MpaRatingNotFoundException {
        log.info("Получен запрос на список фильмов");
        return filmService.getAllFilms();
    }

    @PutMapping("{id}/like/{userId}")
    public void addLike(@PathVariable Long id,
                        @PathVariable Long userId) {
        log.info("Получен запрос на добавление лайка");
        filmService.addLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void removeLike(@PathVariable Long id,
                           @PathVariable Long userId) {
        log.info("Получен запрос на удаление лайка");
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Получен запрос на список популярных фильмов");
        return filmService.getTopFilms(count);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmService.getFilmById(id);
    }
}