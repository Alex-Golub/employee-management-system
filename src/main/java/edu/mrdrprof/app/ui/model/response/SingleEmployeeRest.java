package edu.mrdrprof.app.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * This light object will be sent as response for getEmployee.
 * links will be added to this employee using HATEOAS
 *
 * @author Alex Golub
 * @since 08-Jun-21, 1:05 PM
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SingleEmployeeRest extends RepresentationModel<SingleEmployeeRest> {
  private String publicId;
}
