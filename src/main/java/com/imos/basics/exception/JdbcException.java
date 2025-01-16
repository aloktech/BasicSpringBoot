package com.imos.basics.exception;

/**
 * Class JdbcException TODO
 *
 * @since 24-11-2024
 * @author Alok
 * @version 1.0
 */
public class JdbcException extends Exception {

    public JdbcException() {
    }

    public JdbcException(String message) {
        super(message);
    }

    public JdbcException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcException(Throwable cause) {
        super(cause);
    }

    public JdbcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
