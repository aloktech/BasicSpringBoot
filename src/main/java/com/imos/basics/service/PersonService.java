package com.imos.basics.service;

import com.imos.basics.dto.PersonDto;
import com.imos.basics.exception.DatabaseException;
import com.imos.basics.exception.DuplicateEntityException;
import com.imos.basics.model.Person;
import com.imos.basics.repo.PersonRepo;
import com.imos.basics.utils.ExceptionHandler;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class PersonService TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PersonService implements IPersonService {

  private final PersonRepo personRepo;
  private final ExceptionHandler exceptionHandler;

  @Transactional(rollbackFor = DatabaseException.class)
  @Override
  public void save(PersonDto personDto) throws DatabaseException {
    try {
      Person person = convertToPerson(personDto);
      personRepo.save(person);
    } catch (Exception e) {
      exceptionHandler.handleDatabaseException(
          e,
          th -> {
            String errorMessage = th.getMessage();
            log.debug("{}: {}", th.getClass().getSimpleName(), errorMessage);
            if (errorMessage.startsWith("Duplicate entry")) {
              throw new DuplicateEntityException("Duplicate entry for Person table");
            }
          });
    }
  }

  private static Person convertToPerson(PersonDto personDto) {
    Person person = new Person();
    person.setFirstName(personDto.getFirstName());
    person.setLastName(personDto.getLastName());
    person.setHeight(personDto.getHeight());
    person.setDateOfBirth(personDto.getDateOfBirth());
    person.setMailId(personDto.getMailId());
    return person;
  }

  @Override
  public Optional<PersonDto> findByMailId(String mailId) throws DatabaseException {
    return personRepo.findByMailId(mailId).map(PersonService::convertToPersonDto);
  }

  @Override
  public List<Map<String, Object>> findAddressState(String mailId) throws DatabaseException {
    return personRepo.findAddressState(mailId);
  }

  @Override
  public List<PersonDto> findAll() throws DatabaseException {
    return personRepo.findAll().stream()
        .map(PersonService::convertToPersonDto)
        .collect(Collectors.toList());
  }

  private static PersonDto convertToPersonDto(Person person) {
    PersonDto personDto = new PersonDto();
    personDto.setFirstName(person.getFirstName());
    personDto.setLastName(person.getLastName());
    personDto.setHeight(person.getHeight());
    personDto.setDateOfBirth(person.getDateOfBirth());
    personDto.setMailId(person.getMailId());
    return personDto;
  }
}
