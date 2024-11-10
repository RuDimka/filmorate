package com.example.filmorate.storage.impl;

import com.example.filmorate.dto.UserDto;
import com.example.filmorate.entity.FriendsStatus;
import com.example.filmorate.entity.User;
import com.example.filmorate.exceptions.UserNotFoundException;
import com.example.filmorate.mapper.UserMapperImpl;
import com.example.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserMapperImpl userMapperImpl;

    private User makeUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setLogin(resultSet.getString("login"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setBirthday(resultSet.getDate("birthday").toLocalDate());
        return user;
    }

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
        String sqlQuery = "SELECT * FROM users WHERE ID = ?";
        List<User> userList = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs), id);
        if (userList.isEmpty()) {
            throw new UserNotFoundException("Пользователь с id " + " не найден");
        }
        return userList.get(0);
    }

    @Override
    public User updateUser(UserDto userDto) {
        if (isUserExist(userDto.getId())) {
            String sqlQuery = "update users set " + "login = ?, name = ?, email =?, birthday = ?";
            jdbcTemplate.update(sqlQuery, userDto.getLogin(), userDto.getName(), userDto.getEmail(), userDto.getBirthday());
            return userMapperImpl.userDtoToUser(userDto);
        } else {
            throw new UserNotFoundException("Обновление не возможно, пользователь не найден");
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sqlQuery = "SELECT * FROM users ORDER BY id LIMIT 1";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs));
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

    @Override
    public boolean isUserExist(long id) {
        String sqlQuery = "SELECT COUNT(*) FROM users WHERE ID = ?";
        int countUser = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id);
        return countUser > 0;
    }
}
