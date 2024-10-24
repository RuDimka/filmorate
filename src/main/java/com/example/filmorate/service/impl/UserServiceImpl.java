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
        existingUsers.orElseThrow(() -> new UserNotFoundException("Пользователь с таким id " + userDto.getId() + " не найден"));

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
        log.info("List all users");
        return listUser.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> addFriends(Long id, Long friendId) {
        Optional<User> userOptional = inMemoryUserStorage.findUserById(id);
        Optional<User> friendOptional = inMemoryUserStorage.findUserById(friendId);
        userOptional.orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден."));
        friendOptional.orElseThrow(() -> new FriendNotFoundException("Друг с ID " + friendId + " не найден."));

        User getUser = userOptional.get();
        User getFriend = friendOptional.get();

        if(getUser.containsFriend(friendId)){
            log.warn("Друг существует");
        }
        getUser.addFriend(friendId);
        getFriend.addFriend(id);
        return Optional.of(getUser);
    }

    @Override
    public void removeFriends(Long id, Long friendId) {
        Optional<User> userOptional = inMemoryUserStorage.findUserById(id);
        Optional<User> friendOptional = inMemoryUserStorage.findUserById(friendId);
        userOptional.orElseThrow(() -> new UserNotFoundException("Пользователь или друг не найдены"));
        friendOptional.orElseThrow(() -> new FriendNotFoundException("Друг с ID " + friendId + " не найден."));

        User userRemove = userOptional.get();
        User friendRemove = friendOptional.get();
        userRemove.removeFriends(friendId);
        friendRemove.removeFriends(id);
        log.info("Пользователь {} удален из друзей", id);
        log.info("Пользователь {} удален из друзей", friendId);
    }

    @Override
    public List<User> getCommonFriends(Long id, Long otherId) {
        Optional<User> userOptional = inMemoryUserStorage.findUserById(id);
        Optional<User> otherUserOptional = inMemoryUserStorage.findUserById(otherId);

        if (userOptional.isEmpty() || otherUserOptional.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        User getUser = userOptional.get();
        User getOtherUser = otherUserOptional.get();
        List<Long> friendId1 = getUser.getListFriends();
        List<Long> friendId2 = getOtherUser.getListFriends();

        Set<Long> commonFriends = new HashSet<>(friendId1);
        commonFriends.retainAll(friendId2);

        List<User> friends = new ArrayList<>();
        for (Long friendId : commonFriends) {
            Optional<User> friend = inMemoryUserStorage.findUserById(friendId);
            friend.ifPresent(friends::add);
        }
        log.info("Общий список друзей пользователя {} с пользователем {}: {}", id, otherId, friends);
        return friends;
    }

    @Override
    public List<Optional<User>> getFriends(Long id) {
        Optional<User> userById = inMemoryUserStorage.findUserById(id);
        userById.orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден."));
        User getUser = userById.get();
        List<Optional<User>> friendsList = new ArrayList<>();
        for (Long friendId : getUser.getFriends()) {
            Optional<User> friend = inMemoryUserStorage.findUserById(friendId);
            if (friend.isPresent()) {
                friendsList.add(friend);
            }
        }
        return friendsList;
    }
}