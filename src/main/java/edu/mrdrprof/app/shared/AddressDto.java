package edu.mrdrprof.app.shared;

import edu.mrdrprof.app.io.entity.AddressType;
import lombok.*;

import java.io.Serializable;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 7:54 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddressDto implements Serializable {
  private static final long serialVersionUID = 6123608510381666994L;
  private Long id;
  private String publicId;
  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private AddressType type;
  private EmployeeDto employee;
}
