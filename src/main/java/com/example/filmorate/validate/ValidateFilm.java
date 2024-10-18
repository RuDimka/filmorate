package com.example.filmorate.validate;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.exceptions.ValidateException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidateFilm {
    public static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1892, 12, 28);
    public static final int MAX_DESCRIPTION_LENGTH = 200;

    public void validateToFilms(FilmDto filmDto) {
        if (filmDto.getName().isEmpty()) {
            throw new ValidateException("Не заполнено название фильма");
        }
        if (filmDto.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new ValidateException("Введено не допустимое количество символов, максимальное 200");
        }
        if (filmDto.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            throw new ValidateException("Недопустимая дата релиза фильма");
        }
        if (filmDto.getDuration() < 0) {
            throw new ValidateException("Не корректно указана продолжительность фильма");
        }
    }
}