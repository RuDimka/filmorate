package com.example.filmorate.service.impl;

import com.example.filmorate.dao.Film;
import com.example.filmorate.dao.User;
import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.exceptions.FilmNotFoundException;
import com.example.filmorate.exceptions.UserAlreadyLikedFilmException;
import com.example.filmorate.exceptions.UserNotFoundException;
import com.example.filmorate.exceptions.UserNotLikedFilmException;
import com.example.filmorate.mapper.FilmMapper;
import com.example.filmorate.service.FilmService;
import com.example.filmorate.storage.FilmStorage;
import com.example.filmorate.storage.UserStorage;
import com.example.filmorate.validation.ValidatorFilm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {
    public final FilmStorage filmStorage;
    private final FilmMapper filmMapper;
    private final ValidatorFilm validationFilm;
    private final UserStorage userStorage;

    @Override
    public FilmDto addNewFilm(FilmDto filmDto) {
        log.info("Добавлен новый фильм {}", filmDto.getName());
        validationFilm.validationFilm(filmDto);
        Film newFilm = filmMapper.filmDtoToEntity(filmDto);
        Film savedFilm = filmStorage.saveFilm(newFilm);
        return filmMapper.filmToFIlmDto(savedFilm);
    }

    @Override
    public FilmDto updateFilmById(FilmDto filmDto) {
        log.info("Отредактирована информация по фильму {}", filmDto.getName());
        Optional<Film> existingFilmOpt = filmStorage.getFilmById(filmDto.getId());
        existingFilmOpt.orElseThrow(() -> new FilmNotFoundException("Фильм с id " + filmDto.getId() + " не найден"));

        Film existingFilm = existingFilmOpt.get();
        existingFilm.setName(filmDto.getName());
        existingFilm.setDescription(filmDto.getDescription());
        existingFilm.setReleaseDate(filmDto.getReleaseDate());
        existingFilm.setDuration(filmDto.getDuration());
        Film updateFim = filmStorage.updateFilm(existingFilm);
        return filmMapper.filmToFIlmDto(updateFim);
    }

    @Override
    public List<FilmDto> getAllFilms(FilmDto filmDto) {
        log.info("Список всех фильмов");
        List<Film> listFilm = filmStorage.getAllFilm();
        return listFilm.stream()
                .map(filmMapper::filmToFIlmDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addLike(Long id, Long userId) {
        Optional<Film> filmOptional = filmStorage.getFilmById(id);
        Optional<User> userOptional = userStorage.findUserById(userId);
        filmOptional.orElseThrow(() -> new FilmNotFoundException("Фильм не найден"));
        userOptional.orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        Film filmLiked = filmOptional.get();

        if (filmLiked.getLikedByUsers().contains(userId)) {
            throw new UserAlreadyLikedFilmException("Пользователь с ID " + userId + " уже поставил лайк");
        }
        filmLiked.addLike(userId);
        log.info("Пользователь {} поставил лайк фильму {}", userId, id);
    }

    @Override
    public void removeLike(Long id, Long userId) {
        Optional<Film> filmOptional = filmStorage.getFilmById(id);
        Optional<User> userOptional = userStorage.findUserById(userId);
        Film filmRemoveLike = filmOptional.orElseThrow(() -> new FilmNotFoundException("Фильм не найден"));
        userOptional.orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        if (!filmRemoveLike.getLikedByUsers().contains(userId)) {
            throw new UserNotLikedFilmException("Пользователь с ID " + userId + " не ставил лайк фильму " + id);
        }

        filmRemoveLike.removeLike(userId);
        log.info("Пользователь {} убрал лайк у фильма {}", userId, id);
    }

    @Override
    public List<Film> getTopFilms(Optional<Integer> count) {
        List<Film> filmList = filmStorage.getAllFilm();
        log.info("Список то 10 фильмов {}", filmList);
        return filmList.stream()
                .sorted(Comparator.comparingInt(Film::getLikeCount).reversed())
                .limit(count.orElse(10))
                .collect(Collectors.toList());
    }
}