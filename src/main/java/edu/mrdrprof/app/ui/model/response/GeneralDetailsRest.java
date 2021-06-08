package edu.mrdrprof.app.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 10:26 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeneralDetailsRest extends RepresentationModel<GeneralDetailsRest> {
  private String publicId;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String ssn;
  private String sex;
  private Date hireDate;
}
