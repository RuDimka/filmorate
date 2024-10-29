package com.example.filmorate.validation;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidationFilm {
    public static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1892, 12, 28);
    public static final int MAX_DESCRIPTION_LENGTH = 200;

    public void validateToFilms(FilmDto filmDto) {

        if (filmDto.getName().isEmpty()) {
            throw new ValidationException("Не заполнено название фильма");
        }
        if (filmDto.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new ValidationException("Введено не допустимое количество символов, максимальное 200");
        }
        if (filmDto.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            throw new ValidationException("Недопустимая дата релиза фильма");
        }
        if (filmDto.getDuration() < 0) {
            throw new ValidationException("Не корректно указана продолжительность фильма");
        }
    }
}