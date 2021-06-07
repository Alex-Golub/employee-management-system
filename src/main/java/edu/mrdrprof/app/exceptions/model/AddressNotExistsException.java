package edu.mrdrprof.app.exceptions.model;

/**
 * @author Alex Golub
 * @since 07-Jun-21, 9:14 PM
 */
public class AddressNotExistsException extends RuntimeException {
  private static final long serialVersionUID = 567255690348744065L;

  public AddressNotExistsException(String message) {
    super(message);
  }
}
