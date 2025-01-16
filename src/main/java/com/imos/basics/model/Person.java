package com.imos.basics.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class Person TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
public class Person implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "mail_id")
  private String mailId;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  private Double height;

  @OneToMany
  private List<Address> addressList;
}
