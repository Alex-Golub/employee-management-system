package edu.mrdrprof.app.io.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 7:54 PM
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address implements Serializable {
  private static final long serialVersionUID = 6387939020664788337L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false, length = 20)
  private String publicId; // NB: avoid sending and receiving actual ID of the entity in DB
  @Column(nullable = false, length = 20)
  private String city;
  @Column(nullable = false, length = 20)
  private String country;
  @Column(nullable = false, length = 50)
  private String streetName;
  @Column(nullable = false, length = 10)
  private String postalCode;
  @Column(nullable = false, length = 10)
  private String type;
  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;
}
