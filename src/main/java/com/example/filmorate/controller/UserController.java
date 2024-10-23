package com.example.filmorate.controller;

import com.example.filmorate.dao.User;
import com.example.filmorate.dto.UserDto;
import com.example.filmorate.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        return userService.addFriends(id, friendId);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void removeFriends(@PathVariable Long id,
                              @PathVariable Long friendId) {
        userService.removeFriends(id, friendId);
    }

    @GetMapping("{id}/friends")
    public List<Optional<User>> getFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Set<Long> getCommonFriends(@PathVariable Long id,
                                      @PathVariable Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}