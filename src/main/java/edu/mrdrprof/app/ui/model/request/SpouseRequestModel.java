package edu.mrdrprof.app.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 10:26 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpouseRequestModel {
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String ssn;
  private String sex;
}
