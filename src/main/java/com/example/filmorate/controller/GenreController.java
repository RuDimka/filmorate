//package com.example.filmorate.controller;
//
//import com.example.filmorate.entity.Genre;
//import com.example.filmorate.service.GenreService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/genres")
//public class GenreController {
//
//    private final GenreService genreService;
//
//    @GetMapping("/{id}")
//    public Genre getGenreById(@PathVariable Integer id) {
//        return genreService.getGenreById(id);
//    }
//
//    @GetMapping
//    public List<Genre> getListAllGenres() {
//        return genreService.getAllGenres();
//    }
//}