package com.example.filmorate.mapper;

import com.example.filmorate.dto.FilmDto;
import com.example.filmorate.entity.Film;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FilmMapper {

    Film filmDtoToEntity(FilmDto filmDto);
}