package com.example.filmorate;

import com.example.filmorate.service.impl.dto.FilmDto;
import com.example.filmorate.validate.ValidateFilm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ValidateFilmTest {

    private ValidateFilm validateFilm;

    @BeforeEach
    void setUp() {
        validateFilm = new ValidateFilm();
    }

    @Test
    void validateToFilms() {
        LocalDate date = LocalDate.of(1995, 12, 23);
        FilmDto filmDto = new FilmDto();
        filmDto.setName("Terminator");
        filmDto.setDescription("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.");
        filmDto.setReleaseDate(date);
        filmDto.setDuration(85);
        validateFilm.validateToFilms(filmDto);
    }
}