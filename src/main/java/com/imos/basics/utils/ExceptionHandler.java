package com.imos.basics.utils;

import com.imos.basics.core.ConsumerWithException;
import com.imos.basics.exception.DatabaseException;
import com.imos.basics.exception.DuplicateEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Class ExceptionHandler TODO
 *
 * @author Alok Ranjan Meher
 * @since 12-01-2025
 * @version 1.0
 */
@Slf4j
@Component
public class ExceptionHandler {

  public void handle(Throwable th, ConsumerWithException<Throwable, Exception> consumer)
      throws Exception {
    while (th != null) {
      consumer.accept(th);
      th = th.getCause();
    }
  }

  public void handleDatabaseException(
      Throwable th, ConsumerWithException<Throwable, Exception> consumer) throws DatabaseException {
    try {
      handle(th, consumer);
    } catch (Exception e) {
      if (th instanceof DatabaseException de) {
        throw de;
      }
      throw new DuplicateEntityException(e.getMessage());
    }
  }
}
