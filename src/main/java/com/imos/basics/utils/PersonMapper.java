package com.imos.basics.utils;

import com.imos.basics.dto.PersonDto;
import com.imos.basics.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Interface PersonConvertor TODO
 *
 * @author Alok Ranjan Meher
 * @since 16-01-2025
 * @version 1.0
 */
@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    Person toPerson(PersonDto personDto);

    PersonDto toPersonDto(Person person);
}
