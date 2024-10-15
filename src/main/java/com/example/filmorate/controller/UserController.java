package com.example.filmorate.controller;

import com.example.filmorate.service.impl.dto.UserDto;
import com.example.filmorate.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto addNewUser(@RequestBody UserDto userDto) {
        log.info("Получен запрос на добавление пользователя");
        return userService.addUser(userDto);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto userDto) {
        log.info("Получен запрос на обновление информации о пользователе {}", userDto.getId());
        return userService.updateUser(userDto);
    }

    @GetMapping
    public List<UserDto> getAllUsers(UserDto userDto) {
        log.info("Получен запрос на список всех пользовтаелей");
        return userService.getAllUsers(userDto);
    }
}