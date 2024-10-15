package com.example.filmorate.validate;

import com.example.filmorate.dto.FilmDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidateFilm {
    public static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1892, 12, 28);
    public static final int MAX_DESCRIPTION_LENGTH = 200;

    public void validateToFilms(FilmDto filmDto) {
        if (filmDto.getName().isEmpty()) {
            throw new RuntimeException("Не заполнено название фильма");
        }
        if (filmDto.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
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