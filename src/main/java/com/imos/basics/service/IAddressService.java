package com.imos.basics.service;

import com.imos.basics.dto.AddressDto;

/**
 * Interface IAddressService TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
public interface IAddressService {

    void save(AddressDto addressDto, String mailId);

    int delete(AddressDto addressDto, String mailId);
}
