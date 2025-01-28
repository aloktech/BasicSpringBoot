package com.imos.basics.core;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
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
        .contentType(MediaType.APPLICATION_JSON)
        .body(FailureResponseMessage.builder().errorMessage(ex.getMessage()).build());
  }

  public ResponseEntity<Object> handleValidationException(
          MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    log.error("Exception: {}", ex.getMessage());
    JSONArray errors = new JSONArray();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            objectError -> errors.put(objectError.getDefaultMessage()));
    return ResponseEntity.badRequest()
        .contentType(MediaType.APPLICATION_JSON)
        .body(FailureResponseMessage.<Map<String, Object>>builder().errorMessage("Validation Failed").data(new JSONObject(errors).toMap()).build());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleValidationException(ex, headers, status, request);
  }
}
