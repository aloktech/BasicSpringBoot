package com.imos.basics.service;

import com.imos.basics.exception.JdbcException;

import java.util.List;

/**
 * Interface JdbcService TODO
 *
 * @since 24-11-2024
 * @author Alok
 * @version 1.0
 */
public interface JdbcService {

  <T> void saveEntity(T entity) throws JdbcException;

  <T> List<T> findAll() throws JdbcException;
}
