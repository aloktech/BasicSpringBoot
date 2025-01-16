package com.imos.basics.service;

import com.imos.basics.dto.PersonDto;
import com.imos.basics.exception.DatabaseException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface PersonService TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
public interface IPersonService {

  void save(PersonDto person) throws DatabaseException;

  Optional<PersonDto> findByMailId(String mailId) throws DatabaseException;

  List<Map<String, Object>> findAddressState(String mailId) throws DatabaseException;

  List<PersonDto> findAll() throws DatabaseException;
}
