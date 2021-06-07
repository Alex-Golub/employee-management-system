package edu.mrdrprof.app.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 10:13 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeRest {
  private String publicId;
  private GeneralDetailsRest generalDetails;
  private SpouseRest spouse;
  private List<AddressRest> addresses;
  private List<ChildRest> children;
}
