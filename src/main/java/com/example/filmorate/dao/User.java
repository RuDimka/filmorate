package com.example.filmorate.dao;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Repository
public class User {
    private Long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();

    public void addFriend(Long friend) {
        friends.add(friend);
    }

    public void removeFriends(Long friendId) {
        friends.remove(friendId);
    }

    public List<Long> getListFriends(){
        return new ArrayList<>(friends);
    }

    public boolean containsFriend(Long id){
        return friends.contains(id);
    }
}