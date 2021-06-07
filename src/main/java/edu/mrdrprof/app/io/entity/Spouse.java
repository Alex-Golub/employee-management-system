package edu.mrdrprof.app.io.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 7:54 PM
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Spouse implements Serializable {
  private static final long serialVersionUID = 1659478533708236931L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false, length = 20)
  private String publicId; // NB: avoid sending and receiving actual ID of the entity in DB
  @Column(nullable = false, length = 30)
  private String firstName;
  @Column(nullable = false, length = 30)
  private String lastName;
  @Column(nullable = false, length = 100/*, unique = true*/)
  private String email;
  @Column(nullable = false, length = 50)
  private String phoneNumber;
  @Column(nullable = false, length = 20)
  private String ssn;
  @Column(nullable = false, length = 10)
  private String sex;
  @OneToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;
}
