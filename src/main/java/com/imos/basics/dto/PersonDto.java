package com.imos.basics.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imos.basics.core.LocalDateDeserializer;
import com.imos.basics.core.LocalDateSerializer;
import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.*;
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

  @NotNull
  @NotEmpty
  @NotBlank
  private String firstName;
  private String lastName;
  @NotNull(message = "mailid should not be null")
  @NotEmpty(message = "mailid should not be empty")
  @NotBlank(message = "mailid should not be blank")
  @Email(message = "mailid must be in email pattern")
  @Size(min = 5, max = 50, message = "mailid length should be between 5 and 50")
  private String mailId;
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  @NotNull
  @Past
  private LocalDate dateOfBirth;
  private Double height;
}
