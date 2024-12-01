package com.example.filmorate.service.impl;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.entity.Film;
import com.example.filmorate.entity.Genre;
import com.example.filmorate.exceptions.GenreIdNotFoundException;
import com.example.filmorate.exceptions.MpaIdNotFoundException;
import com.example.filmorate.exceptions.MpaRatingNotFoundException;
import com.example.filmorate.service.FilmService;
import com.example.filmorate.storage.impl.FilmDbStorage;
import com.example.filmorate.storage.impl.GenreDbStorage;
import com.example.filmorate.storage.impl.MpaDbStorage;
import com.example.filmorate.validation.ValidatorFilm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {
    public final FilmDbStorage filmDbStorage;
    private final ValidatorFilm validationFilm;
    private final MpaDbStorage mpaDbStorage;
    private final GenreDbStorage genreDbStorage;

    @Override
    public Film addNewFilm(FilmDto filmDto) throws MpaIdNotFoundException, GenreIdNotFoundException {
        log.info("Добавлен новый фильм {}", filmDto.getName());
        validationFilm.validationFilm(filmDto);
        if (mpaDbStorage.getRatingMpaById(filmDto.getMpa().getId()).isEmpty()) {
            throw new MpaIdNotFoundException("Mpa not found");
        }
        for (Genre genreId : filmDto.getGenres()) {
            if (genreDbStorage.getGenreById(genreId.getId()).isEmpty()) {
                throw new GenreIdNotFoundException("Genre not found");
            }
        }
        return filmDbStorage.saveFilm(filmDto);
    }

    @Override
    public Film updateFilmById(FilmDto filmDto) {
        log.info("Отредактирована информация по фильму {}", filmDto.getName());
        return filmDbStorage.updateFilm(filmDto);
    }

    @Override
    public List<Film> getAllFilms() throws MpaRatingNotFoundException {
        log.info("Список всех фильмов");
        return filmDbStorage.getAllFilm();
    }

    @Override
    public Optional<List<Film>> getTopFilms(int count) {
        log.info("Список популярных фильмов");
        return filmDbStorage.getFilmTop(count);
    }

    @Override
    public void addLike(Long id, Long userId) {
        filmDbStorage.addLike(id, userId);
        log.info("Пользователь {} поставил лайк у фильма {}", userId, id);
    }

    @Override
    public Optional<Film> getFilmById(Long id) {
        log.info("Фильм с ID {}", id);
        return filmDbStorage.findById(id);
    }

    @Override
    public void removeLike(long id, long userId) {
        filmDbStorage.removeLikes(id, userId);
        log.info("Пользователь {} убрал лайк у фильма {}", userId, id);
    }
}