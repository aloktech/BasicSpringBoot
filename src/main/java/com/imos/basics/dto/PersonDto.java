package com.imos.basics.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imos.basics.core.LocalDateDeserializer;
import com.imos.basics.core.LocalDateSerializer;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class Person TODO
 *
 * @since 24-11-2024
 * @author Alok
 * @version 1.0
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class PersonDto implements Serializable {

  private String firstName;
  private String lastName;
  private String mailId;
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate dateOfBirth;
  private Double height;
}
