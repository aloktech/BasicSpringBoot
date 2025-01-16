package com.imos.basics.security;

/**
 * Class KeyCloakException TODO
 *
 * @author Alok Ranjan Meher
 * @since 12-01-2025
 * @version 1.0
 */
public class KeyCloakException extends Exception {
  public KeyCloakException() {}

  public KeyCloakException(String message) {
    super(message);
  }

  public KeyCloakException(String message, Throwable cause) {
    super(message, cause);
  }

  public KeyCloakException(Throwable cause) {
    super(cause);
  }

  public KeyCloakException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
