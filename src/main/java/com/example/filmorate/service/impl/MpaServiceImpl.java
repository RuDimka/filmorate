package com.example.filmorate.service.impl;

import com.example.filmorate.entity.MpaRating;
import com.example.filmorate.exceptions.MpaIdNotFoundException;
import com.example.filmorate.service.MpaService;
import com.example.filmorate.storage.impl.MpaDbStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MpaServiceImpl implements MpaService {

    private final MpaDbStorage mpaDbStorage;

    @Override
    public MpaRating getRatingById(Integer ratingMpaId) {
        if (ratingMpaId > 5) {
            throw new MpaIdNotFoundException("Mpa id not found");
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