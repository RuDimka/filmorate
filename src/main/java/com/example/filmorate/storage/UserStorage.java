package com.example.filmorate.storage;

import com.example.filmorate.dao.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User saveUser(User user);

    Optional<User> findUserById(Long id);

    User updateUser(User user);

    List<User> getAllUsers();
}