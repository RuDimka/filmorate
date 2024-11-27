package com.example.filmorate.storage;

import com.example.filmorate.dto.UserDto;
import com.example.filmorate.entity.User;

import java.util.List;

public interface UserStorage {

    User saveUser(UserDto userDto);

    User updateUser(UserDto userDto);

    List<User> getAllUsers();

    void addFriend(Long id, Long friend);

    void removeFriends(Long userId, Long friendId);

    List<User> getListFriends(Long userId);

    List<User> containsFriend(Long userId, Long otherId);

    boolean isUserExist(long id);
}