package com.example.filmorate.service;

import com.example.filmorate.dto.UserDto;
import com.example.filmorate.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User addUser(UserDto userDto);

    User updateUser(UserDto userDto);

    List<UserDto> getAllUsers(UserDto userDto);

    void addFriends(Long id, Long friendId);

    void removeFriends(Long id, Long friendId);

    List<User> getCommonFriends(Long id, Long otherId);

    List<User> getFriends(Long id);
}