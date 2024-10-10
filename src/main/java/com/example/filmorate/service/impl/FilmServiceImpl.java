package com.example.filmorate.service.impl;

import com.example.filmorate.dao.Film;
import com.example.filmorate.dao.FilmStorageDao;
import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.exceptions.FilmNotFoundException;
import com.example.filmorate.mapper.FilmMapper;
import com.example.filmorate.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.filmorate.dao.FilmStorageDao.MIN_RELEASE_DATE;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {
    private final Film film;
    public final FilmStorageDao filmStorageDao;
    private final FilmMapper filmMapper;

    @Override
    public FilmDto addNewFilm(FilmDto filmDto) {
        log.info("Добавлен фильм {}", filmDto.getName());
        validateFilm(filmDto);
        Film newFilm = filmMapper.filmDtoToEntity(filmDto);
        Film savedFilm = filmStorageDao.saveFilm(newFilm);
        return filmMapper.filmToFIlmDto(savedFilm);
    }

    @Override
    public FilmDto updateFilmById(FilmDto filmDto) {
        log.info("Отредактирована информация по фильму {}", filmDto.getName());
        Optional<Film> existingFilmOpt = filmStorageDao.getFilmById(filmDto.getId());
        if (existingFilmOpt.isPresent()) {
            Film existingFilm = existingFilmOpt.get();
            existingFilm.setName(filmDto.getName());
            existingFilm.setDescription(filmDto.getDescription());
            existingFilm.setReleaseDate(filmDto.getReleaseDate());
            existingFilm.setDuration(filmDto.getDuration());
            Film updateFim = filmStorageDao.updateFilm(existingFilm);
            return filmMapper.filmToFIlmDto(updateFim);
        } else {
            throw new FilmNotFoundException("Фильм с id " + filmDto.getId() + " не найден");
        }
    }

    @Override
    public List<FilmDto> getAllFilms(FilmDto filmDto) {
        log.info("Список всех фильмов");
        List<Film> listFilm = filmStorageDao.getAllFilm(film);
        return listFilm.stream()
                .map(filmMapper::filmToFIlmDto)
                .collect(Collectors.toList());
    }

    public void validateFilm(FilmDto filmDto) {
        if (filmDto.getName().isEmpty()) {
            throw new RuntimeException("Не заполнено название фильма");
        }
        if (filmDto.getDescription().length() > 200) {
            throw new RuntimeException("Введено не допустимое количество символов, максимальное 200");
        }
        if (filmDto.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            throw new RuntimeException("Недопустимая дата релиза фильма");
        }
        if (filmDto.getDuration() < 0) {
            throw new RuntimeException("Не корректно указана продолжительность фильма");
        }
    }
}