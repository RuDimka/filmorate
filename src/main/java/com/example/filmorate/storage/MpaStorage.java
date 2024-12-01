package com.example.filmorate.storage;

import com.example.filmorate.entity.MpaRating;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface MpaStorage {

    Optional<MpaRating> getRatingMpaById(int ratingId) throws SQLException;

    List<MpaRating> getAll();
}