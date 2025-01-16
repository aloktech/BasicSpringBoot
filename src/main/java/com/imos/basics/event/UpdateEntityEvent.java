package com.imos.basics.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * Class UpdateEvent TODO
 *
 * @author Alok Ranjan Meher
 * @since 14-01-2025
 * @version 1.0
 */
@Getter
@ToString
public class UpdateEntityEvent<T> extends ApplicationEvent {

    private final T entity;

    public UpdateEntityEvent(Object source) {
        super(source);
        this.entity = (T) source;
    }
}
