package com.imos.basics.exception;

/**
 * Class DuplicateEntityException TODO
 *
 * @author Alok Ranjan Meher
 * @since 12-01-2025
 * @version 1.0
 */
public class DuplicateEntityException extends DatabaseException {

  public DuplicateEntityException(String message) {
    super(message);
  }
}
