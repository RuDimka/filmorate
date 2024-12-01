package com.example.filmorate.storage.impl;

import com.example.filmorate.constant.SqlRequestConstant;
import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.entity.Film;
import com.example.filmorate.entity.Genre;
import com.example.filmorate.entity.MpaRating;
import com.example.filmorate.exceptions.FilmNotFoundException;
import com.example.filmorate.exceptions.MpaRatingNotFoundException;
import com.example.filmorate.exceptions.UserNotFoundException;
import com.example.filmorate.mapper.FilmMapper;
import com.example.filmorate.storage.FilmStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmMapper filmMapper;
    private final UserDbStorage userDbStorage;
    private final GenreDbStorage genreDbStorage;

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        film.setDuration(rs.getInt("duration"));
        long rating = rs.getLong("rating_id");
        String ratingStr = rs.getString("rating_name");
        MpaRating mpaRating = new MpaRating();
        mpaRating.setId(Math.toIntExact(rating));
        mpaRating.setName(ratingStr);
        film.setMpa(mpaRating);
        film.setLikesCount((rs.getInt("likes_count")));
        return film;
    }

    @Override
    public Film saveFilm(FilmDto filmDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SqlRequestConstant.SQL_QUERY_CREATE_FILM, new String[]{"id"});
            statement.setString(1, filmDto.getName());
            statement.setString(2, filmDto.getDescription());
            statement.setDate(3, Date.valueOf(filmDto.getReleaseDate()));
            statement.setInt(4, filmDto.getDuration());
            statement.setInt(5, filmDto.getMpa().getId());
            return statement;
        }, keyHolder);

        updateGenresById(filmDto);

        Film film = filmMapper.filmDtoToEntity(filmDto);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        saveGenresToDb(film);

        return film;
    }

    private void saveGenresToDb(Film film) {
        long filmId = film.getId();
        if (genreDbStorage.getFilmGenres(filmId).isEmpty()) {
            jdbcTemplate.update(SqlRequestConstant.SQL_QUERY_DELETE_GENRE, filmId);
        }
        if (film.getGenres() != null) {
            final Set<Genre> filmGenres = new HashSet<>(film.getGenres());
            for (Genre genre : filmGenres) {
                jdbcTemplate.update(SqlRequestConstant.SQL_QUERY_INSERT_GENRE, filmId, genre.getId());
            }
        }
    }

    public void updateGenresById(FilmDto filmDto) {
        if (filmDto.getGenres() == null) {
            return;
        }
        List<Genre> genresListWithName = new ArrayList<>();
        Set<Integer> doubleId = new HashSet<>();
        for (Genre genre : filmDto.getGenres()) {
            doubleId.add(genre.getId());
        }
        for (Integer id : doubleId) {
            genreDbStorage.getGenreById(id).ifPresent(genresListWithName::add);
        }
        filmDto.setGenres(genresListWithName);
    }

    @Override
    public Film updateFilm(FilmDto filmDto) {
        if (isFilmExist(filmDto.getId())) {
            jdbcTemplate.update(SqlRequestConstant.SQL_QUERY_UPDATE_FILM,
                    filmDto.getName(), filmDto.getDescription(), filmDto.getReleaseDate(), filmDto.getDuration(), filmDto.getId());
        } else {
            throw new FilmNotFoundException("Фильм не найден");
        }
        updateGenresById(filmDto);

        Film film = filmMapper.filmDtoToEntity(filmDto);
        saveGenresToDb(film);
        return film;
    }

    @Override
    public List<Film> getAllFilm() throws MpaRatingNotFoundException {
        return jdbcTemplate.query(SqlRequestConstant.SQL_QUERY_GET_ALL_FILMS, this::makeFilm);
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        if (checkLike(filmId, userId) == 0) {
            jdbcTemplate.update(SqlRequestConstant.SQL_QUERY_ADD_LIKE, filmId, userId);
        }
    }

    @Override
    public void removeLikes(long filmId, long userId) {
        if (checkLike(filmId, userId) > 0) {
            jdbcTemplate.update(SqlRequestConstant.SQL_QUERY_DELETE_LIKE, userId, filmId);
        }
    }

    public boolean isFilmExist(Long id) {
        int countFilms = jdbcTemplate.queryForObject(SqlRequestConstant.SQL_QUERY_IS_EXIST_FILM, Integer.class, id);
        return countFilms > 0;
    }

    public Optional<List<Film>> getFilmTop(int count) {
        return Optional.of(jdbcTemplate.query(SqlRequestConstant.SQL_QUERY_GET_TOP_FILMS, this::makeFilm, count));
    }

    public int checkLike(final long filmId, final long userId) {
        if (!isFilmExist(filmId)) {
            throw new FilmNotFoundException("Фильм не найден");
        }
        if (!userDbStorage.isUserExist(userId)) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        return jdbcTemplate.queryForObject(SqlRequestConstant.SQL_QUERY_CHECK_LIKE, Integer.class, filmId, userId);
    }

    public Optional<Film> findById(Long id) {
        if (isFilmExist(id)) {
            Film film = jdbcTemplate.queryForObject(SqlRequestConstant.SQL_QUERY_GET_FILM_BY_ID, this::makeFilm, id);
            List<Genre> genres = findByGenresByFilmId(id);
            assert film != null;
            film.setGenres(genres);
            return Optional.of(film);
        } else {
            throw new FilmNotFoundException("Фильм с таким id не найден");
        }
    }

    public List<Genre> findByGenresByFilmId(Long filmId) {
        return jdbcTemplate.query(SqlRequestConstant.SQL_QUERY_GET_GENRE_BY_FILM_ID, (rs, rowNum) -> {
            Genre genre = new Genre();
            genre.setId(rs.getInt("id"));
            genre.setName(rs.getString("name"));
            return genre;
        }, filmId);
    }
}