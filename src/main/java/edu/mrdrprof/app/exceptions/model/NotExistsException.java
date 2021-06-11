package edu.mrdrprof.app.exceptions.model;

/**
 * @author Alex Golub
 * @since 11-Jun-21, 11:25 AM
 */
public class NotExistsException extends RuntimeException {
  private static final long serialVersionUID = 1995876783862971392L;

  public NotExistsException(String message) {
    super(message);
  }
}
