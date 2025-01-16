package com.imos.basics.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class AddressDto TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddressDto implements Serializable {

    private String firstStreet;
    private String secondStreet;
    private String place;
    private String state;
    private String country;
    private String pinCode;
}
