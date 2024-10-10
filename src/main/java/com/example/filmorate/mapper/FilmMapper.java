package com.example.filmorate.mapper;

import com.example.filmorate.dao.Film;
import com.example.filmorate.dto.FilmDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FilmMapper {

    Film filmDtoToEntity(FilmDto filmDto);

    FilmDto filmToFIlmDto(Film film);
}
