package com.imos.basics.config;

import com.imos.basics.event.SaveEntityEvent;
import com.imos.basics.exception.DatabaseException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Configuration;

/**
 * Class AOPConfiguration TODO
 *
 * @since 24-11-2024
 * @author Alok
 * @version 1.0
 */
@Slf4j
@Aspect
@Configuration
public class AOPConfiguration implements ApplicationEventPublisherAware {

  private ApplicationEventPublisher applicationEventPublisher;

  @AfterThrowing(pointcut = "execution(* com.imos.basics.service.*Service.*(..))", throwing = "ex")
  public void handleDatabaseException(DatabaseException ex) {
    log.info("Database Exception: {}", ex.getMessage());
  }

  @After("execution(* com.imos.basics.repo.*Repo.save(..)) && args(entity)")
  public <T> void saveEntity(T entity) {
    log.info("Saving entity: {}", entity);
    applicationEventPublisher.publishEvent(new SaveEntityEvent<>(entity));
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }
}
