package com.example.filmorate.storage;

import com.example.filmorate.entity.FriendsStatus;
import com.example.filmorate.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User saveUser(User user);

    Optional<User> findUserById(Long id);

    User updateUser(User user);

    List<User> getAllUsers();

    public void addFriend(Long friend);

    public void removeFriends(Long friendId);

    public List<Long> getListFriends();

    public boolean containsFriend(Long id);

    public void statusFriends(FriendsStatus friendsStatus);
}