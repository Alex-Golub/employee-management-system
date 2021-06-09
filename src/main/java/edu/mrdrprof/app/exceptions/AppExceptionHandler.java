package edu.mrdrprof.app.exceptions;

import edu.mrdrprof.app.exceptions.model.AddressNotExistsException;
import edu.mrdrprof.app.exceptions.model.ChildNotExistsException;
import edu.mrdrprof.app.exceptions.model.EmployeeExistsException;
import edu.mrdrprof.app.exceptions.model.EmployeeNotExistsException;
import edu.mrdrprof.app.ui.model.response.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Application exceptions will be handled by this handler
 *
 * @author Alex Golub
 * @since 07-Jun-21, 9:28 PM
 */
@RestControllerAdvice
public class AppExceptionHandler {

  @ExceptionHandler(AddressNotExistsException.class)
  public ResponseEntity<HttpResponse> addressNotExists() {
    return generateHttpResponse(HttpStatus.NOT_FOUND, ExceptionMessages.ADDRESS_NOT_FOUND.getMsg());
  }

  @ExceptionHandler(ChildNotExistsException.class)
  public ResponseEntity<HttpResponse> childNotExists() {
    return generateHttpResponse(HttpStatus.NOT_FOUND, ExceptionMessages.CHILD_NOT_FOUND.getMsg());
  }

  @ExceptionHandler(EmployeeExistsException.class)
  public ResponseEntity<HttpResponse> employeeExistsException() {
    return generateHttpResponse(HttpStatus.BAD_REQUEST, ExceptionMessages.EMPLOYEE_EXISTS_BY_EMAIL_SSN.getMsg());
  }

  @ExceptionHandler(EmployeeNotExistsException.class)
  public ResponseEntity<HttpResponse> employeeNotExistsException() {
    return generateHttpResponse(HttpStatus.NOT_FOUND, ExceptionMessages.EMPLOYEE_NOT_EXISTS.getMsg());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class) // display field and message that are incorrect (validator)
  public ResponseEntity<?> handleUserMethodFieldErrors(MethodArgumentNotValidException ex, WebRequest request) {
    final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    final List<CustomFieldError> customFieldErrors = new ArrayList<>();

    for (FieldError fieldError : fieldErrors) {
      final String field = fieldError.getField();
      final String message = fieldError.getDefaultMessage();
      final CustomFieldError customFieldError = CustomFieldError.builder().field(field).message(message).build();

      customFieldErrors.add(customFieldError);
    }

    return ResponseEntity.badRequest().body(customFieldErrors);
  }

  // any other unhandled exceptions will be handled by this handler
  @ExceptionHandler(Exception.class)
  public ResponseEntity<HttpResponse> internalServerErrorException(Exception e) {
    return generateHttpResponse(INTERNAL_SERVER_ERROR, ExceptionMessages.INTERNAL_SERVER_ERROR.getMsg());
  }

  private ResponseEntity<HttpResponse> generateHttpResponse(HttpStatus httpStatus, String msg) {
    return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
            httpStatus.getReasonPhrase(), msg), httpStatus);
  }
}
