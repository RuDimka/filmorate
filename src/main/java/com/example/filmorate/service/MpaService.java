package com.example.filmorate.service;

import com.example.filmorate.entity.MpaRating;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface MpaService {

    MpaRating getRatingById(Integer ratingMpaId) throws SQLException;

    List<MpaRating> getAllRating(MpaRating mpaRating);
}