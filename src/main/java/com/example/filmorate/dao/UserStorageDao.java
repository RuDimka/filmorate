package com.example.filmorate.dao;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserStorageDao {
    private final Map<Long, User> userMap = new HashMap<>();
    private final AtomicLong userIdGenerator = new AtomicLong(1);

    public User saveUser(User user) {
        user.setId(userIdGenerator.getAndIncrement());
        userMap.put(user.getId(), user);
        return user;
    }

    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(userMap.get(id));
    }

    public User updateUser(User user) {
        return userMap.put(user.getId(), user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }
}