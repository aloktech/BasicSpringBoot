package com.imos.basics.controller;

import com.imos.basics.core.FailureResponseMessage;
import com.imos.basics.core.ResponseMessage;
import com.imos.basics.core.SuccessResponseListMessage;
import com.imos.basics.core.SuccessResponseMessage;
import com.imos.basics.dto.PersonDto;
import com.imos.basics.exception.DatabaseException;
import com.imos.basics.service.IPersonService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class BasicController TODO
 *
 * @since 24-11-2024
 * @author Alok
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

  private final IPersonService personService;

  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<? extends ResponseMessage<?>> savePerson(@Valid @RequestBody PersonDto personDto) {
    try {
      personService.save(personDto);

      log.info("Entity saved successfully");
      return ResponseEntity.ok(
          SuccessResponseMessage.builder().message("Entity saved successfully").build());
    } catch (Exception e) {
      log.error("Error while saving entity: {}", e.getMessage());
      return ResponseEntity.badRequest()
          .body(FailureResponseMessage.builder().errorMessage(e.getMessage()).build());
    }
  }

  @GetMapping(value = "/{mail-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonDto fetchPersonByMailId(@PathVariable("mail-id") String mailId)
      throws DatabaseException {
    try {
      return personService
          .findByMailId(mailId)
          .orElseThrow(() -> new EntityNotFoundException("Person not found"));
    } catch (Exception e) {
      log.error("Error while fetching Person with mail id: {} : {}", mailId, e.getMessage());
      throw e;
    }
  }

  @GetMapping(value = "/state/{mail-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  //  public List<Map<String, Object>> fetchPersonAddressStateByMailId(
  //  public ResponseEntity<? extends ResponseMessage<?>> fetchPersonAddressStateByMailId(
  public ResponseMessage<?> fetchPersonAddressStateByMailId(@PathVariable("mail-id") String mailId)
      throws DatabaseException {
    try {
      List<Map<String, Object>> dataList = personService.findAddressState(mailId);
      if (dataList.isEmpty()) {
        throw new EntityNotFoundException("No address state found for mail id: " + mailId);
      } else {
        int count = dataList.size();
        return SuccessResponseListMessage.builderList()
            .data(dataList)
            .count(count)
            .message(count + " entities are found")
            .build();
      }
    } catch (Exception e) {
      log.error(
          "Error while fetching Person and Address with mail id: {} : {}", mailId, e.getMessage());
      throw e;
    }
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<PersonDto> fetchAllPersons(
      @RequestParam(value = "page-size", required = false, defaultValue = "0") int pageSize,
      @RequestParam(value = "off-set", required = false, defaultValue = "0") int offSet)
      throws DatabaseException {
    try {
      if (pageSize == 0) {
        return personService.findAll();
      }
      return personService.findAll(pageSize, offSet);
    } catch (DatabaseException e) {
      log.error("Error while fetching all entities: {}", e.getMessage());
      throw e;
    }
  }
}
