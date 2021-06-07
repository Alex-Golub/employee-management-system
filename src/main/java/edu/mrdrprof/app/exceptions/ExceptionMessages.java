package edu.mrdrprof.app.exceptions;

/**
 * @author Alex Golub
 * @since 07-Jun-21, 9:16 PM
 */
public enum ExceptionMessages {
  INTERNAL_SERVER_ERROR("An error occurred while processing the request"),
  ADDRESS_NOT_FOUND("Address with this id doesn't exists"),
  CHILD_NOT_FOUND("Child with this id doesn't exists"),
  EMPLOYEE_EXISTS_BY_EMAIL_SSN("Employee with such email and SSN is already exists in the system"),
  EMPLOYEE_NOT_EXISTS("Employee with this id doesn't exists"),
  NO_MAPPING("There is no mapping for this URL");

  private final String msg;

  ExceptionMessages(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }
}
