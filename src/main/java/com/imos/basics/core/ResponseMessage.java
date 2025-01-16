package com.imos.basics.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Class EntityResponseBody TODO
 *
 * @since 24-11-2024
 * @author Alok
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public abstract class ResponseMessage<T> implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected T data;
}
