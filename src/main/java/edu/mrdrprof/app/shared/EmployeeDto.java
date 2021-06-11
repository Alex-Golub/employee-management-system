package edu.mrdrprof.app.shared;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 9:29 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeDto implements Serializable {
  private static final long serialVersionUID = 8585435619909091358L;
  private Long id;
  private String publicId;
  private GeneralDetailsDto generalDetails;
  private SpouseDto spouse;
  private List<AddressDto> addresses;
  private List<ChildDto> children;
}
