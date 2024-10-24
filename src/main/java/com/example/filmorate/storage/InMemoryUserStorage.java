package com.example.filmorate.storage;

import com.example.filmorate.dao.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryUserStorage implements UserStorage {

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