//package com.example.filmorate.controller;
//
//import com.example.filmorate.entity.MpaRating;
//import com.example.filmorate.service.MpaService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/mpa")
//@RequiredArgsConstructor
//public class MpaController {
//
//    private final MpaService mpaService;
//
//    @GetMapping("/{id}")
//    public MpaRating getRatingById(@PathVariable Integer id) {
//        return mpaService.getRatingById(id);
//    }
//
//    @GetMapping
//    public List<MpaRating> getListAllMpaRatings(MpaRating mpaRating) {
//        return mpaService.getAllRating(mpaRating);
//    }
//}