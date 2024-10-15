package com.example.filmorate;

import com.example.filmorate.service.impl.dto.UserDto;
import com.example.filmorate.validate.ValidateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ValidateUserTest {

    private ValidateUser validateUser;

    @BeforeEach
    void setUp() {
        validateUser = new ValidateUser();
    }

    @Test
    void validateUserNotNull() {
        LocalDate birthday = LocalDate.of(1988, 12, 12);
        UserDto userDto = new UserDto();
        userDto.setName("Test test");
        userDto.setLogin("Java");
        userDto.setEmail("test@mail.ru");
        userDto.setBirthday(birthday);
        validateUser.validateByUser(userDto);
    }
}