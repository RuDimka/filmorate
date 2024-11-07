package com.example.filmorate.controller;

import com.example.filmorate.entity.User;
import com.example.filmorate.dto.UserDto;
import com.example.filmorate.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PutMapping("{id}/friends/{friendId}")
    public Optional<User> addFriends(@PathVariable Long id,
                                     @PathVariable Long friendId) {
        log.info("ПОлучен запрос на добавления в друзья");
        return userService.addFriends(id, friendId);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void removeFriends(@PathVariable Long id,
                              @PathVariable Long friendId) {
        log.info("Получен запрос на удаление из друзей");
        userService.removeFriends(id, friendId);
    }

    @GetMapping("{id}/friends")
    public List<Optional<User>> getFriends(@PathVariable Long id) {
        log.info("Получен запрос на список друзей");
        return userService.getFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Long id,
                                       @PathVariable Long otherId) {
        log.info("ПОлучен запрос на список общих друзей");
        return userService.getCommonFriends(id, otherId);
    }
}