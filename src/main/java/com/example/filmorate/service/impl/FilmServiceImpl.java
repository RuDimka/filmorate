package com.example.filmorate.service.impl;

import com.example.filmorate.dao.Film;
import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.exceptions.FilmNotFoundException;
import com.example.filmorate.mapper.FilmMapper;
import com.example.filmorate.service.FilmService;
import com.example.filmorate.storage.InMemoryFilmStorage;
import com.example.filmorate.validate.ValidateFilm;
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
    public final InMemoryFilmStorage inMemoryFilmStorage;
    private final FilmMapper filmMapper;
    private final ValidateFilm validateFilm;

    @Override
    public FilmDto addNewFilm(FilmDto filmDto) {
        log.info("Добавлен фильм {}", filmDto.getName());
        validateFilm.validateToFilms(filmDto);
        Film newFilm = filmMapper.filmDtoToEntity(filmDto);
        Film savedFilm = inMemoryFilmStorage.saveFilm(newFilm);
        return filmMapper.filmToFIlmDto(savedFilm);
    }

    @Override
    public FilmDto updateFilmById(FilmDto filmDto) {
        log.info("Отредактирована информация по фильму {}", filmDto.getName());
        Optional<Film> existingFilmOpt = inMemoryFilmStorage.getFilmById(filmDto.getId());
        if (existingFilmOpt.isPresent()) {
            Film existingFilm = existingFilmOpt.get();
            existingFilm.setName(filmDto.getName());
            existingFilm.setDescription(filmDto.getDescription());
            existingFilm.setReleaseDate(filmDto.getReleaseDate());
            existingFilm.setDuration(filmDto.getDuration());
            Film updateFim = inMemoryFilmStorage.updateFilm(existingFilm);
            return filmMapper.filmToFIlmDto(updateFim);
        } else {
            throw new FilmNotFoundException("Фильм с id " + filmDto.getId() + " не найден");
        }
    }

    @Override
    public List<FilmDto> getAllFilms(FilmDto filmDto) {
        log.info("Список всех фильмов");
        List<Film> listFilm = inMemoryFilmStorage.getAllFilm();
        return listFilm.stream()
                .map(filmMapper::filmToFIlmDto)
                .collect(Collectors.toList());
    }
}