package edu.mrdrprof.app.ui.model.response;

import edu.mrdrprof.app.io.entity.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 10:26 PM
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChildRest extends RepresentationModel<ChildRest> {
  private String publicId;
  private String firstName;
  private String lastName;
  private Sex sex;
  private Date birthDate;
}
