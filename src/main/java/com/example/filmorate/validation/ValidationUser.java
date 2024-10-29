package com.example.filmorate.validation;

import com.example.filmorate.dto.UserDto;
import com.example.filmorate.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidationUser {

    public void validateByUser(UserDto userDto) {
        if (userDto.getEmail().isEmpty() || !userDto.getEmail().contains("@")) {
            throw new ValidationException("Не заполнен или не корректный адрес электронной почты");
        }

        if (userDto.getName() == null) {
            userDto.setName(userDto.getLogin());
        }
        LocalDate today = LocalDate.now();
        if (userDto.getBirthday().isAfter(today)) {
            throw new ValidationException("Указана не корректная дата рождения");
        }
    }
}