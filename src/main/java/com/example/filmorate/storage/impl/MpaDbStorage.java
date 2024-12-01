package com.example.filmorate.storage.impl;

import com.example.filmorate.constant.SqlRequestConstant;
import com.example.filmorate.entity.MpaRating;
import com.example.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<MpaRating> getRatingMpaById(int ratingId) {
        List<MpaRating> ratingList = jdbcTemplate.query(SqlRequestConstant.SQL_QUERY_GET_MPA_RATING_BY_ID,
                (rs, rowNum) -> new MpaRating(rs.getInt("id"),
                        rs.getString("rating_name")), ratingId);
        return ratingList.stream().findFirst();
    }

    @Override
    public List<MpaRating> getAll() {
        return jdbcTemplate.query(SqlRequestConstant.SQL_QUERY_GER_ALL_MPA_RATING,
                (rs, rowNum) -> new MpaRating(rs.getInt("id"),
                        rs.getString("rating_name")));
    }
}