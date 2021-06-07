package edu.mrdrprof.app.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 7:54 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpouseDto {
  private Long id;
  private String publicId;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String ssn;
  private String sex;
  private EmployeeDto employee;
}
