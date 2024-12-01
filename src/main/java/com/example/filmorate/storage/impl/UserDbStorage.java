package com.example.filmorate.storage.impl;

import com.example.filmorate.constant.SqlRequestConstant;
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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(SqlRequestConstant.SQL_QUERY_ADD_NEW_USER, new String[]{"id"});
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
    public User updateUser(UserDto userDto) {
        if (isUserExist(userDto.getId())) {
            jdbcTemplate.update(SqlRequestConstant.SQL_QUERY_UPDATE_USER,
                    userDto.getLogin(), userDto.getName(), userDto.getEmail(), userDto.getBirthday(), userDto.getId());
            return userMapperImpl.userDtoToUser(userDto);
        } else {
            throw new UserNotFoundException("Обновление не возможно, пользователь не найден");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SqlRequestConstant.SQL_QUERY_GET_ALL_USERS, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        if (!isUserExist(userId) || !isUserExist(friendId)) {
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        jdbcTemplate.update(SqlRequestConstant.SQL_QUERY_ADD_FRIEND,
                userId, friendId, "CONFIRMED");
    }

    @Override
    public void removeFriends(Long userId, Long friendId) {
        if (!isUserExist(userId) || !isUserExist(friendId)) {
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        boolean isDelete = jdbcTemplate.update(SqlRequestConstant.SQL_QUERY_DELETE_FRIEND,
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
        return jdbcTemplate.query(SqlRequestConstant.SQL_QUERY_GET_LIST_FRIENDS, new BeanPropertyRowMapper<>(User.class), userId);
    }

    @Override
    public List<User> containsFriend(Long userId, Long otherId) {
        if (!isUserExist(userId) || !isUserExist(otherId)) {
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        return jdbcTemplate.query(SqlRequestConstant.SQL_QUERY_GET_CONTAINS_FRIENDS, new BeanPropertyRowMapper<>(User.class), userId, otherId);
    }

    @Override
    public boolean isUserExist(long id) {
        int countUser = jdbcTemplate.queryForObject(SqlRequestConstant.SQL_QUERY_IS_EXIST_USER, Integer.class, id);
        return countUser > 0;
    }
}