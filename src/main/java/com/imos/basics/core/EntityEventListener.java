package com.imos.basics.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Class EntityEventListener TODO
 *
 * @author Alok Ranjan Meher
 * @since 14-01-2025
 * @version 1.0
 */
@Slf4j
@Component
public class EntityEventListener {

  @EventListener
  <T> void handleSaveEntityEvent(SaveEntityEvent<T> event) {
    log.info("Entity saved: {}", event.toString());
  }

  @EventListener
  <T> void handleUpdateEntityEvent(UpdateEntityEvent<T> event) {
    log.info("Entity updated: {}", event.toString());
  }
}
