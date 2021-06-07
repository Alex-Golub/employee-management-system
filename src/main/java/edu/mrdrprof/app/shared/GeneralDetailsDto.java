package edu.mrdrprof.app.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class GeneralDetailsDto implements Serializable {
  private static final long serialVersionUID = 8524609962376142285L;
  private Long id;
  private String publicId;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String ssn;
  private String sex;
  private Date hireDate;
  private EmployeeDto employee;
}
