package com.imos.basics.security;

/**
 * Class JwtValidationFailedException TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
public class JwtValidationFailedException extends Exception {

  public JwtValidationFailedException(String message) {
    super(message);
  }
}
