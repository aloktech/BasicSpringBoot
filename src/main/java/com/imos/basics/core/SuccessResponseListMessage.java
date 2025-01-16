package com.imos.basics.core;

import lombok.Getter;

/**
 * Class SuccessResponseListMessage TODO
 *
 * @author Alok Ranjan Meher
 * @since 12-01-2025
 * @version 1.0
 */
@Getter
public class SuccessResponseListMessage<T> extends SuccessResponseMessage<T> {

  private final int count;

  public SuccessResponseListMessage(T entity, String message, int count) {
    super(entity, message);
    this.count = count;
  }

  public static <E> SuccessResponseListMessageBuilder<E> builderList() {
    return new SuccessResponseListMessageBuilder<>();
  }

  public static class SuccessResponseListMessageBuilder<E>
      implements Builder<SuccessResponseListMessage<E>> {

    private int count;
    private E data;
    private String message;

    public SuccessResponseListMessageBuilder() {}

    public SuccessResponseListMessageBuilder<E> count(int count) {
      this.count = count;
      return this;
    }

    public SuccessResponseListMessageBuilder<E> data(E data) {
      this.data = data;
      return this;
    }

    public SuccessResponseListMessageBuilder<E> message(String message) {
      this.message = message;
      return this;
    }

    @Override
    public SuccessResponseListMessage<E> build() {
      return new SuccessResponseListMessage<>(data, message, count);
    }
  }
}
