package com.imos.basics.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class RestExceptionHandler TODO
 *
 * @since 24-11-2024
 * @author Alok
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<FailureResponseMessage<?>> handleException(Exception ex) {
    log.error("Exception: {}", ex.getMessage());
    return ResponseEntity.badRequest()
        .body(FailureResponseMessage.builder().errorMessage(ex.getMessage()).build());
  }
}
