package com.example.filmorate.storage;

import com.example.filmorate.dto.UserDto;
import com.example.filmorate.entity.FriendsStatus;
import com.example.filmorate.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User saveUser(UserDto userDto);

    User findUserById(Long id);

    User updateUser(UserDto userDto);

    List<User> getAllUsers();

    void addFriend(Long id, Long friend);

    void removeFriends(Long userId, Long friendId);

    List<User> getListFriends(Long userId);

    List<User> containsFriend(Long userId, Long otherId);

    boolean isUserExist(long id);
}