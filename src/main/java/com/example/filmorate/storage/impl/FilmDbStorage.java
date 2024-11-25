package com.example.filmorate.storage.impl;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.entity.Film;
import com.example.filmorate.entity.Genre;
import com.example.filmorate.exceptions.FilmNotFoundException;
import com.example.filmorate.exceptions.GenreNotFoundException;
import com.example.filmorate.exceptions.MpaRatingNotFoundException;
import com.example.filmorate.exceptions.UserNotFoundException;
import com.example.filmorate.mapper.FilmMapper;
import com.example.filmorate.storage.FilmStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmMapper filmMapper;
    private final UserDbStorage userDbStorage;
    private final GenreDbStorage genreDbStorage;
    private final MpaDbStorage mpaDbStorage;

    @Override
    public Film saveFilm(FilmDto filmDto) {
        String sqlQuery = "INSERT INTO films (name, description, release_date, duration, rating_id) VALUES(?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"id"});
            statement.setString(1, filmDto.getName());
            statement.setString(2, filmDto.getDescription());
            statement.setDate(3, Date.valueOf(filmDto.getReleaseDate()));
            statement.setInt(4, filmDto.getDuration());
            statement.setInt(5, filmDto.getMpa().getId());
            try {
                filmDto.setMpa(mpaDbStorage.getRatingMpaById(filmDto.getMpa().getId()));
            } catch (MpaRatingNotFoundException e) {
                throw new MpaRatingNotFoundException("MPA рейтинг не существует");
            }
            return statement;
        }, keyHolder);

        updateMpaRating(filmDto);
        updateGenresById(filmDto);

        Film film = filmMapper.filmDtoToEntity(filmDto);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        saveGenresToDb(film);

        return film;
    }

    private void saveGenresToDb(Film film) {
        long filmId = film.getId();
        if (genreDbStorage.getFilmGenres(filmId).isEmpty()) {
            String sql = "DELETE FROM film_genres WHERE film_id = ?";
            jdbcTemplate.update(sql, filmId);
        }
        if (film.getGenres() != null) {
            final Set<Genre> filmGenres = new HashSet<>(film.getGenres());
            String sql = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
            for (Genre genre : filmGenres) {
                jdbcTemplate.update(sql, filmId, genre.getId());
            }
        }
    }

    public void updateMpaRating(FilmDto filmDto) throws MpaRatingNotFoundException {
        filmDto.setMpa(mpaDbStorage.getRatingMpaById(filmDto.getMpa().getId()));
    }

    public void updateGenresById(FilmDto filmDto) throws GenreNotFoundException {
        if (filmDto.getGenres() == null) {
            return;
        }
        List<Genre> genresListWithName = new ArrayList<>();
        Set<Integer> doubleId = new HashSet<>();
        for (Genre genre : filmDto.getGenres()) {
            if (!doubleId.contains(genre.getId())) {
                doubleId.add(genre.getId());
                genresListWithName.add(genreDbStorage.getGenreById(genre.getId()));
            }
        }
        filmDto.setGenres(genresListWithName);
    }

    @Override
    public Film updateFilm(FilmDto filmDto) {
        if (isFilmExist(filmDto.getId())) {
            jdbcTemplate.update("UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?",
                    filmDto.getName(), filmDto.getDescription(), filmDto.getReleaseDate(), filmDto.getDuration());
        } else {
            throw new FilmNotFoundException("Фильм не найден");
        }
        updateMpaRating(filmDto);
        updateGenresById(filmDto);

        Film film = filmMapper.filmDtoToEntity(filmDto);
        saveGenresToDb(film);
        return film;
    }

    @Override
    public List<Film> getAllFilm() throws MpaRatingNotFoundException {
        return jdbcTemplate.query("SELECT * FROM  films ORDER BY id", new BeanPropertyRowMapper<>(Film.class));
    }

    @Override
    public void saveLike(Film film) {
        jdbcTemplate.update("DELETE FROM likes WHERE film_id = ?", film.getId());

        String sql = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
        Set<Long> likes = film.getLikes();
        for (var like : likes) {
            jdbcTemplate.update(sql, film.getId(), like);
        }
    }

    @Override
    public void loadLikes(Film film) {
        String sql = "SELECT user_id FROM likes WHERE film_id = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, film.getId());
        while (sqlRowSet.next()) {
            film.addLike(sqlRowSet.getLong("user_id"));
        }
    }

    @Override
    public void removeLikes(long filmId, long userId) {
        if (checkLike(filmId, userId) == 0) {
            String sql = "DELETE FROM  likes WHERE film_id = ? AND user_id = ? ";
            jdbcTemplate.update(sql, userId, filmId);
        }
    }

    public boolean isFilmExist(Long id) {
        int countFilms = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM films WHERE id = ?", Integer.class, id);
        return countFilms > 0;
    }

    public List<Film> getFilmTop(int count) {
        String sqlQuery = "SELECT * FROM films WHERE rating_id = ?";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Film.class), count);
    }

    public int checkLike(final long filmId, final long userId) {
        if (!isFilmExist(filmId)) {
            throw new FilmNotFoundException("Фильм не найден");
        }
        if (!userDbStorage.isUserExist(userId)) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        String sql = "SELECT COUNT(*) FROM likes WHERE film_id = ? AND user_id = ? LIMIT 1";
        return jdbcTemplate.queryForObject(sql, Integer.class, filmId, userId);
    }

    public Film findById(Long id) {
        if (isFilmExist(id)) {
            String sql = "SELECT * FROM films WHERE id = ?";
            List<Film> films = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Film.class), id);
            return films.get(0);
        } else {
            throw new FilmNotFoundException("Фидьм с таким id не найден");
        }
    }

    @Override
    public Set<Genre> getGenresByFilm(Film film) {
        String sql = "SELECT g.id, g.name FROM genres g JOIN film_genres fg ON g.id = fg.film_id WHERE fg.film_id = ?";
        return new HashSet<>(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Genre.class), film.getId()));
    }
}