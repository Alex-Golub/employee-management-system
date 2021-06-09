package edu.mrdrprof.app.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 10:26 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressRequestModel {
  @NotBlank
  private String city;
  @NotBlank
  private String country;
  @NotBlank
  private String streetName;
  @NotBlank
  private String postalCode;
  @NotBlank
  private String type;
}
