package com.example.filmorate.controller;

import com.example.filmorate.entity.MpaRating;
import com.example.filmorate.service.MpaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
@Slf4j
public class MpaController {

    private final MpaService mpaService;

    @GetMapping("/{id}")
    public MpaRating getRatingById(@PathVariable Integer id) throws SQLException {
        log.info("Запрос на получение рейтинга по ID");
        return mpaService.getRatingById(id);
    }

    @GetMapping
    public List<MpaRating> getListAllMpaRatings(MpaRating mpaRating) {
        log.info("Запрос на получение списка ретингов");
        return mpaService.getAllRating(mpaRating);
    }
}