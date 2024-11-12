package com.example.filmorate.service.impl;

import com.example.filmorate.entity.FriendsStatus;
import com.example.filmorate.entity.User;
import com.example.filmorate.dto.UserDto;
import com.example.filmorate.exceptions.FriendNotFoundException;
import com.example.filmorate.exceptions.UserNotFoundException;
import com.example.filmorate.mapper.UserMapper;
import com.example.filmorate.service.UserService;
import com.example.filmorate.storage.impl.UserDbStorage;
import com.example.filmorate.validation.ValidatorUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserDbStorage userDbStorage;
    private final ValidatorUser validateUser;

    @Override
    public User addUser(UserDto userDto) {
        log.info("Добавлен пользователь {}", userDto.getName());
        validateUser.validationUser(userDto);
        return userDbStorage.saveUser(userDto);
    }

    @Override
    public User updateUser(UserDto userDto) {
        log.info("Обновлена информация по пользователю {}", userDto.getId());
        return userDbStorage.updateUser(userDto);
    }

    @Override
    public List<UserDto> getAllUsers(UserDto userDto) {
        List<User> listUser = userDbStorage.getAllUsers();
        log.info("Список всех пользователей");
        return listUser.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addFriends(Long id, Long friendId) {
        log.info("Пользователь {} добавил в друзья пользователя {}", id, friendId);
        userDbStorage.addFriend(id, friendId);
    }

    @Override
    public void removeFriends(Long id, Long friendId) {
        userDbStorage.removeFriends(id, friendId);
        log.info("Пользователь {} удален из друзей", id);
        log.info("Пользователь {} удален из друзей", friendId);
    }

    @Override
    public List<User> getCommonFriends(Long id, Long otherId) {
        return userDbStorage.containsFriend(id, otherId);
    }

    @Override
    public List<User> getFriends(Long id) {
        log.info("Список друзей пользователя {}", id);
        return userDbStorage.getListFriends(id);
    }
}