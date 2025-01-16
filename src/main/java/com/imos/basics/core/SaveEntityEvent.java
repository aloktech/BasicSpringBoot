package com.imos.basics.core;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * Class SaveEvent TODO
 *
 * @author Alok Ranjan Meher
 * @since 14-01-2025
 * @version 1.0
 */
@Getter
@ToString
public class SaveEntityEvent<T> extends ApplicationEvent {

  private final T entity;

  public SaveEntityEvent(Object source) {
    super(source);
    this.entity = (T) source;
  }
}
