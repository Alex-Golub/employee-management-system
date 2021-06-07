package edu.mrdrprof.app.exceptions.model;

/**
 * @author Alex Golub
 * @since 07-Jun-21, 9:23 PM
 */
public class EmployeeNotExistsException extends RuntimeException {
  private static final long serialVersionUID = 3698205360515384135L;

  public EmployeeNotExistsException(String message) {
    super(message);
  }
}
