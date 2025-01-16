package com.imos.basics.core;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.imos.basics.utils.BasicConstant;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class LocalDateDeserialzable TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
    return LocalDate.parse(
        jsonParser.getText(), DateTimeFormatter.ofPattern(BasicConstant.DATE_FORMAT));
    }
}
