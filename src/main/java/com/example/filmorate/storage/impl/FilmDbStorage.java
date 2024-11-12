package com.example.filmorate.storage.impl;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.entity.Film;
import com.example.filmorate.exceptions.FilmNotFoundException;
import com.example.filmorate.mapper.FilmMapperImpl;
import com.example.filmorate.storage.FilmStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmMapperImpl filmMapperImpl;

    @Override
    public Film saveFilm(FilmDto filmDto) {
        String sqlQuery = "INSERT INTO films (name, description, release_date, duration, rate, rating_id) VALUES(?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[] {"id"});
            statement.setString(1, filmDto.getName());
            statement.setString(2, filmDto.getDescription());
            statement.setDate(3, Date.valueOf(filmDto.getReleaseDate()));
            statement.setInt(4, filmDto.getDuration());
            statement.setInt(5, filmDto.getRate());
            statement.setInt(6, filmDto.getMpa().getId());
            return statement;
        }, keyHolder);


        filmDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return filmMapperImpl.filmDtoToEntity(filmDto);
    }

    @Override
    public Film updateFilm(FilmDto filmDto) {
        if (isFilmExist(filmDto.getId())){
            jdbcTemplate.update("UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?",
                    filmDto.getName(), filmDto.getDescription(), filmDto.getReleaseDate(), filmDto.getDuration());
            return filmMapperImpl.filmDtoToEntity(filmDto);
        } else {
            throw new FilmNotFoundException("Фильм не найден");
        }
    }

    @Override
    public List<Film> getAllFilm() {
        return jdbcTemplate.query("SELECT * FROM films", new BeanPropertyRowMapper<>(Film.class));
    }

    @Override
    public Optional<Film> getFilmById(Long id) {
        return Optional.empty();
    }

    @Override
    public void addLike(Long userId) {

    }

    @Override
    public void removeLike(Long userId) {

    }

    @Override
    public int getLikeCount() {
        return 0;
    }

    public boolean isFilmExist(Long id) {
        int countFilms = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM films WHERE id = ?", Integer.class, id);
        return countFilms > 0;
    }
}