package edu.mrdrprof.app.io.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 7:54 PM
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GeneralDetails implements Serializable {
  private static final long serialVersionUID = 8524609962376142285L;
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
  @Column(nullable = false, length = 20/*, unique = true*/)
  private String ssn; // Social Security Number
  @Column(nullable = false, length = 10)
  private String sex;
  @Column(nullable = false)
  private Date hireDate;
  @OneToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;
}
