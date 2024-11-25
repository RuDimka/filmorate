package com.example.filmorate.storage.impl;

import com.example.filmorate.entity.MpaRating;
import com.example.filmorate.exceptions.MpaRatingNotFoundException;
import com.example.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public MpaRating getRatingMpaById(int ratingId) {
        String sql = "SELECT * FROM ratings WHERE id = ?";
        List<MpaRating> ratingList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MpaRating.class), ratingId);
        if (!ratingList.isEmpty()) {
            return ratingList.get(0);
        }
        log.warn("Rating {} not found", ratingId);
        throw new MpaRatingNotFoundException("Индекс MPA рейтинга не найден " + ratingId);
    }

    @Override
    public List<MpaRating> getAll() {
        String sql = "SELECT * FROM ratings ORDER BY id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MpaRating.class));
    }
}
