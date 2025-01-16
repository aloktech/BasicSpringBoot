package com.imos.basics.event;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Class SaveEntityEventListener TODO
 *
 * @author Alok Ranjan Meher
 * @since 14-01-2025
 * @version 1.0
 */
@Slf4j
@ToString
@Component
public class SaveEntityEventListener<T extends ApplicationEvent> implements ApplicationListener<T> {

  @Override
  public void onApplicationEvent(T event) {
    log.info("Saving entity: {}", event);
  }

  @Override
  public boolean supportsAsyncExecution() {
    log.info("Checking if async execution is enabled");
    return ApplicationListener.super.supportsAsyncExecution();
  }
}
