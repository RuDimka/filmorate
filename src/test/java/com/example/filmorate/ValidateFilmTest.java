package com.example.filmorate;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.validation.ValidatorFilm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ValidateFilmTest {

    private ValidatorFilm validateFilm;

    @BeforeEach
    void setUp() {
        validateFilm = new ValidatorFilm();
    }

    @Test
    void validateToFilms() {
        LocalDate date = LocalDate.of(1995, 12, 23);
        FilmDto filmDto = new FilmDto();
        filmDto.setName("Terminator");
        filmDto.setDescription("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.");
        filmDto.setReleaseDate(date);
        filmDto.setDuration(85);
        validateFilm.validationFilm(filmDto);
    }
}