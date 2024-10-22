package com.example.filmorate.service.impl;

import com.example.filmorate.dao.User;
import com.example.filmorate.dto.UserDto;
import com.example.filmorate.exceptions.FriendAlreadyExistsException;
import com.example.filmorate.exceptions.FriendNotFoundException;
import com.example.filmorate.exceptions.UserNotFoundException;
import com.example.filmorate.mapper.UserMapper;
import com.example.filmorate.service.UserService;
import com.example.filmorate.storage.InMemoryUserStorage;
import com.example.filmorate.validate.ValidateUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final InMemoryUserStorage inMemoryUserStorage;
    private final ValidateUser validateUser;
    //private final User user;

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

        if (getUser.getFriends().contains(friendId)) {
            throw new FriendAlreadyExistsException("Пользователь " + id + " уже является другом " + friendId);
        }
        getUser.addFriend(friendId);
        getFriend.addFriend(id);
        inMemoryUserStorage.updateUser(getUser);
        inMemoryUserStorage.updateUser(getFriend);
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
        inMemoryUserStorage.saveUser(userRemove);
        inMemoryUserStorage.saveUser(friendRemove);
        log.info("Пользователь {} удален из друзей", id);
        log.info("Пользователь {} удален из друзей", friendId);
    }

    @Override
    public List<UserDto> getCommonFriends(Long id, Long otherId) {
        return List.of();
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