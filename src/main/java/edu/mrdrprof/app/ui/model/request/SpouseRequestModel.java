package edu.mrdrprof.app.ui.model.request;

import edu.mrdrprof.app.io.entity.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 10:26 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpouseRequestModel {
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  @Email
  private String email;
  @NotBlank
  private String phoneNumber;
  @NotBlank
  private String ssn;
  private Sex sex;
}
