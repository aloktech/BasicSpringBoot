package com.imos.basics.core;

/**
 * Interface ConsumerWithException TODO
 *
 * @author Alok Ranjan Meher
 * @since 12-01-2025
 * @version 1.0
 */
public interface ConsumerWithException<T, E extends Exception> {
  void accept(T t) throws E;
}
