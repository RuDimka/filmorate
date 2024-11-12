package com.example.filmorate.service.impl;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.entity.Film;
import com.example.filmorate.exceptions.FilmNotFoundException;
import com.example.filmorate.mapper.FilmMapper;
import com.example.filmorate.service.FilmService;
import com.example.filmorate.storage.impl.FilmDbStorage;
import com.example.filmorate.storage.impl.UserDbStorage;
import com.example.filmorate.validation.ValidatorFilm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {
    public final FilmDbStorage filmDbStorage;
    private final FilmMapper filmMapper;
    private final ValidatorFilm validationFilm;
    private final UserDbStorage userDbStorage;

    @Override
    public Film addNewFilm(FilmDto filmDto) {
        log.info("Добавлен новый фильм {}", filmDto.getName());
        validationFilm.validationFilm(filmDto);
        return filmDbStorage.saveFilm(filmDto);
    }

    @Override
    public Film updateFilmById(FilmDto filmDto) {
        log.info("Отредактирована информация по фильму {}", filmDto.getName());
        return filmDbStorage.updateFilm(filmDto);
    }

    @Override
    public List<FilmDto> getAllFilms(FilmDto filmDto) {
        List<Film> listFilm = filmDbStorage.getAllFilm();
        log.info("Список всех фильмов");
        return listFilm.stream()
                .map(filmMapper::filmToFIlmDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addLike(Long id, Long userId) {
        filmDbStorage.addLike(userId);
        log.info("Пользователь {} поставил лайк фильму {}", userId, id);
    }

    @Override
    public void removeLike(Long id, Long userId) {
        filmDbStorage.removeLike(userId);
        log.info("Пользователь {} убрал лайк у фильма {}", userId, id);
    }

    @Override
    public List<Film> getTopFilms(Optional<Integer> count) {
        List<Film> filmList = filmDbStorage.getAllFilm();
        log.info("Список то 10 фильмов {}", filmList);
        return filmList.stream()
                .sorted()
                .limit(count.orElse(10))
                .collect(Collectors.toList());
    }
}