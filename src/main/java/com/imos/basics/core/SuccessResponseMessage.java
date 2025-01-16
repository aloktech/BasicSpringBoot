package com.imos.basics.core;

import lombok.Getter;

/**
 * Class SuccessResponseBody TODO
 *
 * @since 24-11-2024
 * @author Alok
 * @version 1.0
 */
@Getter
public class SuccessResponseMessage<T> extends ResponseMessage<T> {

  protected final String message;

  public SuccessResponseMessage(T entity, String message) {
    super(entity);
    this.message = message;
  }

  public static <E> SuccessResponseMessageBuilder<E> builder() {
    return new SuccessResponseMessageBuilder<>();
  }

  public static class SuccessResponseMessageBuilder<E>
      implements Builder<SuccessResponseMessage<E>> {
    private String message;
    private E data;

    public SuccessResponseMessageBuilder() {}

    public SuccessResponseMessageBuilder<E> data(E data) {
      this.data = data;
      return this;
    }

    public SuccessResponseMessageBuilder<E> message(String message) {
      this.message = message;
      return this;
    }

    @Override
    public SuccessResponseMessage<E> build() {
      return new SuccessResponseMessage<>(data, message);
    }
  }
}
