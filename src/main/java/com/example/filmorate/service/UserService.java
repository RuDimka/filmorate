package com.example.filmorate.service;

import com.example.filmorate.dao.User;
import com.example.filmorate.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface UserService {
    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    List<UserDto> getAllUsers(UserDto userDto);

    Optional<User> addFriends(Long id, Long friendId);

    void removeFriends(Long id, Long friendId);

    Set<Long> getCommonFriends(Long id, Long otherId);

    List<Optional<User>> getFriends(Long id);
}