package com.example.filmorate.dao;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Repository
public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Long> likedByUsers = new HashSet<>();

    public void addLike(Long userId) {
        likedByUsers.add(userId);
    }

    public void removeLike(Long userId) {
        likedByUsers.remove(userId);
    }
}