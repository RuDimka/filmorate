package com.example.filmorate.service.impl;

import com.example.filmorate.dao.User;
import com.example.filmorate.dao.UserStorageDao;
import com.example.filmorate.dto.UserDto;
import com.example.filmorate.exceptions.UserNotFoundException;
import com.example.filmorate.mapper.UserMapper;
import com.example.filmorate.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserStorageDao userStorageDao;
    private final User user;

    @Override
    public UserDto addUser(UserDto userDto) {
        log.info("Добавлен пользователь {}", userDto.getName());
        validateUser(userDto);
        User newUser = userMapper.userDtoToUser(userDto);
        User savedUser = userStorageDao.saveUser(newUser);
        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        log.info("Обновлена информация по пользователю {}", userDto.getId());
        Optional<User> existingUsers = userStorageDao.findUserById(userDto.getId());
        if (existingUsers.isPresent()) {
            User existingUser = existingUsers.get();
            existingUser.setName(userDto.getName());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setLogin(userDto.getLogin());
            existingUser.setBirthday(userDto.getBirthday());
            User updateUser = userStorageDao.updateUser(existingUser);
            return userMapper.userToUserDto(updateUser);
        } else {
            throw new UserNotFoundException("Пользователь с таким id " + userDto.getId() + " не найден");
        }
    }

    @Override
    public List<UserDto> getAllUsers(UserDto userDto) {
        log.info("Список пользователей");
        List<User> listUser = userStorageDao.getAllUsers(user);
        return listUser.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public void validateUser(UserDto userDto) {
        if (userDto.getEmail().isEmpty() || !userDto.getEmail().contains("@")) {
            throw new RuntimeException("Не заполнен адрес электронной почты");
        }
//        if (userDto.getLogin() == null || !userDto.getLogin().contains(" ")) {
//            throw new RuntimeException("Не указано или не коррентное имя");
//        }
        if (userDto.getName() == null) {
            userDto.setName(userDto.getLogin());
        }
        LocalDate today = LocalDate.now();
        if (userDto.getBirthday().isAfter(today)) {
            throw new RuntimeException("Указана не корректная дата рождения");
        }
    }
}