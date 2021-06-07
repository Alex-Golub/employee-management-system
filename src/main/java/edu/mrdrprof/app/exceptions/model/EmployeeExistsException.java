package edu.mrdrprof.app.exceptions.model;

/**
 * @author Alex Golub
 * @since 07-Jun-21, 9:13 PM
 */
public class EmployeeExistsException extends RuntimeException {
  private static final long serialVersionUID = -9168327694054878872L;

  public EmployeeExistsException(String message) {
    super(message);
  }
}
