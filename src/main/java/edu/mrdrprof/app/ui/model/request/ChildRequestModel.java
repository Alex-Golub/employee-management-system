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
public class ChildRequestModel {
  private String firstName;
  private String lastName;
  @JsonDeserialize(using = DateHandler.class)
  private Date birthDate;
}
