package edu.mrdrprof.app.exceptions.model;

/**
 * @author Alex Golub
 * @since 07-Jun-21, 9:15 PM
 */
public class ChildNotExistsException extends RuntimeException {
  private static final long serialVersionUID = 8587433342428247605L;

  public ChildNotExistsException(String message) {
    super(message);
  }
}
