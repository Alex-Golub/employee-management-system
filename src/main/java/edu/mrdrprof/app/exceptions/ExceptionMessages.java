package edu.mrdrprof.app.exceptions;

/**
 * @author Alex Golub
 * @since 07-Jun-21, 9:16 PM
 */
public enum ExceptionMessages {
  INTERNAL_SERVER_ERROR("An error occurred while processing the request"),
  DETAILS_NOT_EXISTS("Details with id: %s not exists"),
  SPOUSE_NOT_EXISTS("Spouse with id: %s not exists"),
  ADDRESS_NOT_EXISTS("Address with id: %s not exists"),
  ADDRESSES_NOT_EXISTS("There are no address for employee with id %s"),
  CHILD_NOT_EXISTS("Child with id: %s not exists"),
  CHILDREN_NOT_EXISTS("Employee with id %s, has no children"),
  EMPLOYEE_EXISTS_BY_EMAIL_SSN("Employee with email %s and SSN %s is already exists in the system"),
  EMPLOYEE_NOT_EXISTS("Employee with id: %s doesn't exists"),
  NO_MAPPING("There is no mapping for this URL");

  private final String msg;

  ExceptionMessages(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }
}
