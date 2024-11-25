package com.example.filmorate.service.impl;

import com.example.filmorate.entity.MpaRating;
import com.example.filmorate.exceptions.MpaIdNotFoundException;
import com.example.filmorate.service.MpaService;
import com.example.filmorate.storage.impl.MpaDbStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {

    private final MpaDbStorage mpaDbStorage;

    @Override
    public MpaRating getRatingById(Integer ratingMpaId) {
        if (ratingMpaId > 5) {
            throw new MpaIdNotFoundException("Mpa id not found");
        }
        return mpaDbStorage.getRatingMpaById(ratingMpaId);
    }

    @Override
    public List<MpaRating> getAllRating(MpaRating mpaRating) {
        return mpaDbStorage.getAll();
    }
}
