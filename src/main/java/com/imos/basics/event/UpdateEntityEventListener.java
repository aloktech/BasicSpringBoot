package com.imos.basics.event;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Class UpdateEntityEventListener TODO
 *
 * @author Alok Ranjan Meher
 * @since 14-01-2025
 * @version 1.0
 */
@Slf4j
@ToString
public class UpdateEntityEventListener implements ApplicationListener<UpdateEntityEvent<String>> {
  @Override
  public void onApplicationEvent(UpdateEntityEvent<String> event) {
    log.info("Updating entity: {}", event);
  }

  @Override
  public boolean supportsAsyncExecution() {
    log.info("Checking if async execution is enabled");
    return ApplicationListener.super.supportsAsyncExecution();
  }
}
