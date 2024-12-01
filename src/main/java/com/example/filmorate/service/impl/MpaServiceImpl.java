package com.example.filmorate.service.impl;

import com.example.filmorate.entity.MpaRating;
import com.example.filmorate.exceptions.MpaRatingNotFoundException;
import com.example.filmorate.service.MpaService;
import com.example.filmorate.storage.impl.MpaDbStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MpaServiceImpl implements MpaService {

    private final MpaDbStorage mpaDbStorage;

    @Override
    public Optional<MpaRating> getRatingById(Integer ratingMpaId) throws MpaRatingNotFoundException {
        if (mpaDbStorage.getRatingMpaById(ratingMpaId).isEmpty()) {
            throw new MpaRatingNotFoundException("MPA Id not found");
        }
        log.info("Рейтинг с ID {}", ratingMpaId);
        return mpaDbStorage.getRatingMpaById(ratingMpaId);
    }

    @Override
    public List<MpaRating> getAllRating(MpaRating mpaRating) {
        log.info("Список рейтингов");
        return mpaDbStorage.getAll();
    }
}