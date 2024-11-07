package com.example.filmorate.storage.impl;

import com.example.filmorate.entity.FriendsStatus;
import com.example.filmorate.entity.User;
import com.example.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public User saveUser(User user) {
        String sqlQuery = "INSERT INTO users (login, name, email, birthday) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, user.getLogin(), user.getName(), user.getEmail(), user.getBirthday());
        return user;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public void addFriend(Long friend) {

    }

    @Override
    public void removeFriends(Long friendId) {

    }

    @Override
    public List<Long> getListFriends() {
        return List.of();
    }

    @Override
    public boolean containsFriend(Long id) {
        return false;
    }

    @Override
    public void statusFriends(FriendsStatus friendsStatus) {

    }
}
