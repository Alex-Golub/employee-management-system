package edu.mrdrprof.app.io.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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
public class Child implements Serializable {
  private static final long serialVersionUID = -3906891424799252905L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false, length = 20)
  private String publicId; // NB: avoid sending and receiving actual ID of the entity in DB
  @Column(nullable = false, length = 20)
  private String firstName;
  @Column(nullable = false, length = 20)
  private String lastName;
  @Column(nullable = false)
  private Date birthDate;
  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private Sex sex;
  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;
}
