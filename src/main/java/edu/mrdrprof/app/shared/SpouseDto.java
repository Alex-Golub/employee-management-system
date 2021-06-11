package edu.mrdrprof.app.shared;

import edu.mrdrprof.app.io.entity.Sex;
import lombok.*;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 7:54 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SpouseDto {
  private Long id;
  private String publicId;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String ssn;
  private Sex sex;
  private EmployeeDto employee;
}
