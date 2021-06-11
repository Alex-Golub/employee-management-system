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
public class ChildDto implements Serializable {
  private static final long serialVersionUID = 1204791017194443441L;
  private Long id;
  private String publicId;
  private String firstName;
  private String lastName;
  private Sex sex;
  private Date birthDate;
  private EmployeeDto employee;
}
