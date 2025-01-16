package com.imos.basics.core;

import java.util.Optional;
import lombok.Getter;

/**
 * Class FailureResponseBody TODO
 *
 * @since 24-11-2024
 * @author Alok
 * @version 1.0
 */
@Getter
public class FailureResponseMessage<T> extends ResponseMessage<T> {

  private final String errorMessage;

  public FailureResponseMessage(T data, String errorMessage) {
    super(data);
    this.errorMessage = errorMessage;
  }

  public FailureResponseMessage(String errorMessage) {
    super(null);
    this.errorMessage = errorMessage;
  }

  public static <E> FailureResponseMessageBuilder<E> builder() {
    return new FailureResponseMessageBuilder<>();
  }

  public Optional<String> getErrorMessage() {
    return Optional.ofNullable(errorMessage);
  }

  public static class FailureResponseMessageBuilder<E>
      implements Builder<FailureResponseMessage<E>> {
    private String errorMessage;
    private E data;

    public FailureResponseMessageBuilder() {}

    public FailureResponseMessageBuilder<E> data(E data) {
      this.data = data;
      return this;
    }

    public FailureResponseMessageBuilder<E> errorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
      return this;
    }

    @Override
    public FailureResponseMessage<E> build() {
      return new FailureResponseMessage<>(data, errorMessage);
    }
  }
}
