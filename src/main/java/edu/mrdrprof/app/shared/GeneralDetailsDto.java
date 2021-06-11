package edu.mrdrprof.app.shared;

import edu.mrdrprof.app.io.entity.Sex;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 7:54 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GeneralDetailsDto implements Serializable {
  private static final long serialVersionUID = 8524609962376142285L;
  private Long id;
  private String publicId;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String ssn;
  private Sex sex;
  private Date hireDate;
  private EmployeeDto employee;
}
