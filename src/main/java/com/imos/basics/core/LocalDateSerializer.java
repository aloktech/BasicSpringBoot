package com.imos.basics.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.imos.basics.utils.BasicConstant;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class LocalDateSerializable TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {
  @Override
  public void serialize(
      LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    jsonGenerator.writeString(
        localDate.format(DateTimeFormatter.ofPattern(BasicConstant.DATE_FORMAT)));
  }
}
