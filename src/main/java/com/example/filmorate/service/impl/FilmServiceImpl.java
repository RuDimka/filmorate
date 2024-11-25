package com.example.filmorate.service.impl;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.entity.Film;
import com.example.filmorate.entity.Genre;
import com.example.filmorate.exceptions.MpaRatingNotFoundException;
import com.example.filmorate.service.FilmService;
import com.example.filmorate.storage.impl.FilmDbStorage;
import com.example.filmorate.validation.ValidatorFilm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {
    public final FilmDbStorage filmDbStorage;
    private final ValidatorFilm validationFilm;

    @Override
    public Film addNewFilm(FilmDto filmDto) throws SQLException {
        log.info("Добавлен новый фильм {}", filmDto.getName());
        validationFilm.validationFilm(filmDto);
        return filmDbStorage.saveFilm(filmDto);
    }

    @Override
    public Film updateFilmById(FilmDto filmDto) throws SQLException {
        log.info("Отредактирована информация по фильму {}", filmDto.getName());
        return filmDbStorage.updateFilm(filmDto);
    }

    @Override
    public List<Film> getAllFilms() throws MpaRatingNotFoundException {
        log.info("Список всех фильмов");
        return filmDbStorage.getAllFilm();
    }

    @Override
    public List<Film> getTopFilms(int count) {
        List<Film> films = filmDbStorage.getFilmTop(count);
        films.sort(Comparator.comparing(Film::getLikesCount).reversed());
        if(count > films.size()) {
            count = films.size();
        }
        return new ArrayList<>(films.subList(0, count));
    }

    @Override
    public void addLike(Long id, Long userId) {
        Film film = filmDbStorage.findById(id);
        film.addLike(userId);
        filmDbStorage.saveLike(film);
        log.info("Пользователь {} поставил лайк у фильма {}", userId, id);
    }

    @Override
    public Film getFilmById(Long id) {
        return filmDbStorage.findById(id);
    }

    @Override
    public void removeLike(long id, long userId) {
        Film film = filmDbStorage.findById(id);
        film.removeLike(userId);
        filmDbStorage.saveLike(film);
        log.info("Пользователь {} убрал лайк у фильма {}", userId, id);
    }
}