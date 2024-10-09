package com.example.filmorate.dao;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserStorageDao {
    private final Map<Long, User> userMap = new HashMap<>();
    private final AtomicLong userIdGenerator = new AtomicLong(1);

}