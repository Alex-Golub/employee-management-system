package edu.mrdrprof.app.ui.model.request;

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
public class EmployeeRequestModel {
  private GeneralDetailsRequestModel generalDetails;
  private SpouseRequestModel spouse;
  private List<AddressRequestModel> addresses;
  private List<ChildRequestModel> children;
}
