package com.example.filmorate.service;

import com.example.filmorate.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    List<UserDto> getAllUsers(UserDto userDto);
}