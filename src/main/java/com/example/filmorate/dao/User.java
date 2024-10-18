package com.example.filmorate.dao;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Data
@Repository
public class User {
    private Long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
    private Set<Long> friends;

    public void addFriend(Long friend) {
        friends.add(friend);
    }
}