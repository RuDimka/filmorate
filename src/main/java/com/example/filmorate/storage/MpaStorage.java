package com.example.filmorate.storage;

import com.example.filmorate.entity.MpaRating;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MpaStorage {

    MpaRating getRatingMpaById(int ratingId);

    List<MpaRating> getAll();
}
