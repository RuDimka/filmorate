package com.example.filmorate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private MpaRating mpa;
    private List<Genre> genres;
    private final Set<Long> likes = new HashSet<>();

    public void addLike(Long like) {
        likes.add(like);
    }

    public void removeLike(Long like) {
        likes.remove(like);
    }

    public int getLikesCount() {
        return likes.size();
    }

    public Set<Long> getLikes() {
        return new HashSet<>(likes);
    }
}