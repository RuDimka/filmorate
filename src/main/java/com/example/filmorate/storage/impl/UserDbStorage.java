package com.example.filmorate.storage.impl;

import com.example.filmorate.dto.UserDto;
import com.example.filmorate.entity.User;
import com.example.filmorate.exceptions.UserNotFoundException;
import com.example.filmorate.exceptions.UserRemoveException;
import com.example.filmorate.mapper.UserMapperImpl;
import com.example.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserMapperImpl userMapperImpl;

    @Override
    public User saveUser(UserDto userDto) {
        String sqlQuery = "INSERT INTO users (login, name, email, birthday) VALUES(?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, userDto.getLogin());
            stmt.setString(2, userDto.getName());
            stmt.setString(3, userDto.getEmail());
            stmt.setDate(4, Date.valueOf(userDto.getBirthday()));
            return stmt;
        }, keyHolder);
        userDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return userMapperImpl.userDtoToUser(userDto);
    }

    @Override
    public User findUserById(Long id) {
        List<User> userList = jdbcTemplate.query("SELECT * FROM users WHERE ID = ?", new BeanPropertyRowMapper<>(User.class));
        if (userList.isEmpty()) {
            throw new UserNotFoundException("Пользователь с id " + " не найден");
        }
        return userList.get(0);
    }

    @Override
    public User updateUser(UserDto userDto) {
        if (isUserExist(userDto.getId())) {
            jdbcTemplate.update("UPDATE users SET login = ?, name = ?, email = ?, birthday = ?",
                    userDto.getLogin(), userDto.getName(), userDto.getEmail(), userDto.getBirthday());
            return userMapperImpl.userDtoToUser(userDto);
        } else {
            throw new UserNotFoundException("Обновление не возможно, пользователь не найден");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY id LIMIT 1", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        if (!isUserExist(userId) || !isUserExist(friendId)) {
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        jdbcTemplate.update("INSERT INTO friends (user_id, friend_id, status) VALUES (?, ?, ?)",
                userId, friendId, "CONFIRMED");
    }

    @Override
    public void removeFriends(Long userId, Long friendId) {
        if (!isUserExist(userId) || !isUserExist(friendId)) {
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        boolean isDelete = jdbcTemplate.update("DELETE FROM friends WHERE user_id = ? AND friend_id = ?",
                userId, friendId) < 1;
        if (isDelete) {
            throw new UserRemoveException("Пользователь в друзях не найден");
        }
    }

    @Override
    public List<User> getListFriends(Long userId) {
        if (!isUserExist(userId)) {
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }

        String sqlQuery = "SELECT * FROM users WHERE users.id IN " +
                "(SELECT friends.friend_id FROM friends WHERE friends.user_id = ? AND status = 'CONFIRMED');";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(User.class), userId);
    }

    @Override
    public List<User> containsFriend(Long userId, Long otherId) {
        if (!isUserExist(userId) || !isUserExist(otherId)) {
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        String sqlQuery = "SELECT * FROM users WHERE id IN " +
                "(SELECT DISTINCT (friends.friend_id) FROM friends WHERE user_id = ? AND status = 'CONFIRMED'" +
                " AND friend_id IN (SELECT friend_id FROM friends WHERE user_id = ? AND status = 'CONFIRMED'))";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(User.class), userId, otherId);
    }

    @Override
    public boolean isUserExist(long id) {
        int countUser = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE ID = ?", Integer.class, id);
        return countUser > 0;
    }
}
