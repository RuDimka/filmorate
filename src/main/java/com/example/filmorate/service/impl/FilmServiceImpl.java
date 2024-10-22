package com.example.filmorate.service.impl;

import com.example.filmorate.dao.Film;
import com.example.filmorate.dao.User;
import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.exceptions.*;
import com.example.filmorate.mapper.FilmMapper;
import com.example.filmorate.service.FilmService;
import com.example.filmorate.storage.InMemoryFilmStorage;
import com.example.filmorate.storage.InMemoryUserStorage;
import com.example.filmorate.validate.ValidateFilm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    private final InMemoryUserStorage inMemoryUserStorage;

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

    @Override
    public void addLike(Long id, Long userId) {
        Optional<Film> filmOptional = inMemoryFilmStorage.getFilmById(id);
        if (filmOptional.isEmpty()) {
            throw new FilmNotFoundException("Фильм не найден");
        }
        Film filmLiked = filmOptional.get();

        Optional<User> userOptional = inMemoryUserStorage.findUserById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        if (filmLiked.getLikedByUsers().contains(userId)) {
            throw new UserAlreadyLikedFilmException("Пользователь с ID " + userId + " уже поставил лайк");
        }
        filmLiked.addLike(userId);
        inMemoryFilmStorage.saveFilm(filmLiked);
    }

    @Override
    public ResponseEntity<Void> removeLike(Long id, Long userId) {
        Optional<Film> filmOptional = inMemoryFilmStorage.getFilmById(id);
        if (filmOptional.isEmpty()) {
            throw new FilmNotFoundException("Фильм не найден");
        }

        Film filmRemove = filmOptional.get();

        Optional<User> userOptional = inMemoryUserStorage.findUserById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        if (!filmRemove.getLikedByUsers().contains(userId)) {
            throw new UserNotLikedFilmException("Пользователь с ID " + userId + " не ставил лайк фильму " + id);
        }
        filmRemove.removeLike(userId);
        inMemoryFilmStorage.saveFilm(filmRemove);

        return ResponseEntity.ok().build();
    }

    @Override
    public List<Film> getTopFilms(Long count) {
        List<Film> filmById = inMemoryFilmStorage.getAllFilm();
        return filmById.stream()
                .sorted(Comparator.comparingInt(film -> film.getLikedByUsers().size()))
                .limit(10)
                .collect(Collectors.toList());
    }
}