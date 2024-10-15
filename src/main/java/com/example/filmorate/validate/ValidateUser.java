package com.example.filmorate.validate;

import com.example.filmorate.dto.UserDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidateUser {

    public void validateByUser(UserDto userDto) {
        if (userDto.getEmail().isEmpty() || !userDto.getEmail().contains("@")) {
            throw new RuntimeException("Не заполнен или не корректный адрес электронной почты");
        }

        if (userDto.getName() == null) {
            userDto.setName(userDto.getLogin());
        }
        LocalDate today = LocalDate.now();
        if (userDto.getBirthday().isAfter(today)) {
            throw new RuntimeException("Указана не корректная дата рождения");
        }
    }
}