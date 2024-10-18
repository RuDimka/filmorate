package com.example.filmorate.service.impl;

import com.example.filmorate.dao.User;
import com.example.filmorate.dto.UserDto;
import com.example.filmorate.exceptions.FriendNotFoundException;
import com.example.filmorate.exceptions.UserNotFoundException;
import com.example.filmorate.mapper.UserMapper;
import com.example.filmorate.service.UserService;
import com.example.filmorate.storage.InMemoryUserStorage;
import com.example.filmorate.validate.ValidateUser;
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
    private final InMemoryUserStorage inMemoryUserStorage;
    private final ValidateUser validateUser;
    private final User user;

    @Override
    public UserDto addUser(UserDto userDto) {
        log.info("Добавлен пользователь {}", userDto.getName());
        validateUser.validateByUser(userDto);
        User newUser = userMapper.userDtoToUser(userDto);
        User savedUser = inMemoryUserStorage.saveUser(newUser);
        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        log.info("Обновлена информация по пользователю {}", userDto.getId());
        Optional<User> existingUsers = inMemoryUserStorage.findUserById(userDto.getId());
        if (existingUsers.isEmpty()) {
            throw new UserNotFoundException("Пользователь с таким id " + userDto.getId() + " не найден");
        }
        User existingUser = existingUsers.get();
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setLogin(userDto.getLogin());
        existingUser.setBirthday(userDto.getBirthday());
        User updateUser = inMemoryUserStorage.updateUser(existingUser);
        return userMapper.userToUserDto(updateUser);
    }

    @Override
    public List<UserDto> getAllUsers(UserDto userDto) {
        log.info("Список пользователей");
        List<User> listUser = inMemoryUserStorage.getAllUsers();
        return listUser.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> addFriends(Long id, Long friendId) {
        Optional<User> userById = inMemoryUserStorage.findUserById(id);
        Optional<User> friendsById = inMemoryUserStorage.findUserById(friendId);
        if (userById.isPresent() && friendsById.isPresent()) {
            User userAdd = userById.get();
            User friend = friendsById.get();
            userAdd.addFriend(friend.getId());
            inMemoryUserStorage.saveUser(userAdd);
            return Optional.of(userAdd);
        } else {
            throw new FriendNotFoundException("Пользователь или друг не найдены");
        }
    }

    @Override
    public void removeFriends(Long id, Long friendId) {
        Optional<User> userOptional = inMemoryUserStorage.findUserById(id);
        Optional<User> friendOptional = inMemoryUserStorage.findUserById(friendId);
        if (userOptional.isPresent() && friendOptional.isPresent()) {
            User userRemove = userOptional.get();
            inMemoryUserStorage.removeFriends(friendId);
            inMemoryUserStorage.saveUser(userRemove);
        } else {
            throw new UserNotFoundException("Пользователь или друг не найдены");
        }
    }

    @Override
    public List<UserDto> getCommonFriends(Long id, Long otherId) {
//        Optional<User> userOptional = inMemoryUserStorage.findUserById(id);
//        Optional<User> otherUserOptional = inMemoryUserStorage.findUserById(otherId);
//        if (userOptional.isPresent() && otherUserOptional.isPresent()) {
//            User thisUsers = userOptional.get();
//            User otherUser = otherUserOptional.get();
//
//            Set<Long> thisUserFriends = new HashSet<>(thisUsers.getFriends());
//            Set<Long> otherUsersFriends = new HashSet<>(otherUser.getFriends());
//            thisUserFriends.retainAll(otherUsersFriends);
//
//            List<UserDto> commonFriends = new ArrayList<>();
//            for(Long friendId : thisUserFriends) {
//                Optional<User> friendOptional = inMemoryUserStorage.findUserById(friendId);
//                friendOptional.isPresent();
//        }
//        return commonFriends;
//    }
        return List.of();
    }

    @Override
    public List<Optional<User>> getFriends(Long id) {
        Optional<User> userById = inMemoryUserStorage.findUserById(id);
        List<Optional<User>> friendsList = new ArrayList<>();
        if (userById.isPresent() && user.getFriends() != null) {
            for (Long friendId : user.getFriends()) {
                Optional<User> friend = inMemoryUserStorage.findUserById(friendId);
                if (friend.isPresent()) {
                    friendsList.add(friend);
                }
            }
        }
        return friendsList;
    }
}