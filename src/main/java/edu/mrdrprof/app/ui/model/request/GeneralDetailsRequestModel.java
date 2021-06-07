package edu.mrdrprof.app.ui.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.mrdrprof.app.shared.utils.DateHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 10:26 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeneralDetailsRequestModel {
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String ssn;
  private String sex;
  @JsonDeserialize(using = DateHandler.class)
  private Date hireDate; // "dd-MM-yyyy"
}
