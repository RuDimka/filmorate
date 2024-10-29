package com.example.filmorate;

import com.example.filmorate.dto.UserDto;
import com.example.filmorate.validation.ValidatorUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ValidateUserTest {

    private ValidatorUser validateUser;

    @BeforeEach
    void setUp() {
        validateUser = new ValidatorUser();
    }

    @Test
    void validateUserNotNull() {
        LocalDate birthday = LocalDate.of(1988, 12, 12);
        UserDto userDto = new UserDto();
        userDto.setName("Test test");
        userDto.setLogin("Java");
        userDto.setEmail("test@mail.ru");
        userDto.setBirthday(birthday);
        validateUser.validationUser(userDto);
    }
}