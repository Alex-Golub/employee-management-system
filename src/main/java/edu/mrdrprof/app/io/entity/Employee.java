package edu.mrdrprof.app.io.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 7:51 PM
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Employee implements Serializable {
  private static final long serialVersionUID = -4549561583330412721L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false, length = 20)
  private String publicId; // NB: avoid sending and receiving actual ID of the entity in DB
  @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
  private GeneralDetails generalDetails;
  @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
  private Spouse spouse;
  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Address> addresses;
  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Child> children;
}
