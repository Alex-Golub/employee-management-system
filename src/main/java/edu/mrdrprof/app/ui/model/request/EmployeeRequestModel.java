package edu.mrdrprof.app.ui.model.request;

import lombok.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 10:13 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeRequestModel {
  @Valid
  private GeneralDetailsRequestModel generalDetails;
  @Valid
  private SpouseRequestModel spouse;
  @Valid
  private List<@Valid AddressRequestModel> addresses;
  @Valid
  private List<@Valid ChildRequestModel> children;
}
