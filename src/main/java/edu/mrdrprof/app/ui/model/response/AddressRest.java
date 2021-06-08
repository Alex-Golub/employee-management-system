package edu.mrdrprof.app.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 10:26 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressRest extends RepresentationModel<AddressRest> {
  private String publicId;
  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;
}
