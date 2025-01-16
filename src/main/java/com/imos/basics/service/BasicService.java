package com.imos.basics.service;

import com.imos.basics.core.JdbcException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Class BasicService TODO
 *
 * @since 24-11-2024
 * @author Alok
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class BasicService {

  private final JdbcService jdbcService;

  public <T> void saveEntity(T entity) throws JdbcException {
    jdbcService.saveEntity(entity);
    throw new RuntimeException("Error in saving entity");
  }

  public <T> List<T> findAll() throws JdbcException {
    return jdbcService.findAll();
  }
}
