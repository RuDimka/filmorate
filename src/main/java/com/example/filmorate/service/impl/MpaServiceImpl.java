package com.example.filmorate.service.impl;

import com.example.filmorate.entity.MpaRating;
import com.example.filmorate.service.MpaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {
    @Override
    public MpaRating getRatingById(Integer ratingMpaId) {
        return null;
    }

    @Override
    public List<MpaRating> getAllRating(MpaRating mpaRating) {
        return List.of();
    }
}
