package com.imos.basics.service;

import com.imos.basics.dto.AddressDto;
import com.imos.basics.model.Address;
import com.imos.basics.model.Person;
import com.imos.basics.repo.AddressRepo;
import com.imos.basics.repo.PersonRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Class AddressService TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class AddressService implements IAddressService {

  private final AddressRepo addressRepo;
  private final PersonRepo personRepo;

  @Override
  public void save(AddressDto addressDto, String mailId) {
    Address address = new Address();
    address.setFirstStreet(addressDto.getFirstStreet());
    address.setSecondStreet(addressDto.getSecondStreet());
    address.setPlace(addressDto.getPlace());
    address.setCountry(addressDto.getCountry());
    address.setState(addressDto.getState());
    address.setPinCode(addressDto.getPinCode());
    Person person =
        personRepo
            .findByMailId(mailId)
            .orElseThrow(() -> new EntityNotFoundException("Person not found"));
    address.setPerson(person);
    addressRepo.save(address);
  }

  @Override
  public int delete(AddressDto addressDto, String mailId) {
    return addressRepo.deleteByPersonMailId(mailId);
  }
}
