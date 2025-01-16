package com.imos.basics.service;

import com.imos.basics.exception.JdbcException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class DefaultJdbcService TODO
 *
 * @since 24-11-2024
 * @author Alok
 * @version 1.0
 */
@Slf4j
@Service
public class DefaultJdbcService implements JdbcService {
    
    @Override
    public <T> void saveEntity(T entity) throws JdbcException {
        log.info("Entity Saved in Database");
        throw new JdbcException("Database do not exist");
    }

    @Override
    public <T> List<T> findAll() throws JdbcException {
        return List.of();
    }
}
