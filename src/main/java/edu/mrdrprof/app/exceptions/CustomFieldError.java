package edu.mrdrprof.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Alex Golub
 * @since 09-Jun-21, 10:08 PM
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomFieldError {
  private String field;
  private String message;
}
