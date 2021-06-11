package edu.mrdrprof.app.exceptions;

import edu.mrdrprof.app.exceptions.model.EmployeeExistsException;
import edu.mrdrprof.app.exceptions.model.NotExistsException;
import edu.mrdrprof.app.ui.model.response.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Application exceptions will be handled by this handler
 *
 * @author Alex Golub
 * @since 07-Jun-21, 9:28 PM
 */
@RestControllerAdvice
public class AppExceptionHandler {

  @ExceptionHandler(NotExistsException.class)
  public ResponseEntity<HttpResponse> detailsNotExists(NotExistsException e) {
    return generateHttpResponse(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(EmployeeExistsException.class)
  public ResponseEntity<HttpResponse> employeeExistsException(EmployeeExistsException e) {
    return generateHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  // display field and message that are incorrect (validator)
  public ResponseEntity<?> handleUserMethodFieldErrors(MethodArgumentNotValidException ex) {
    final List<HttpResponse> httpResponses = new ArrayList<>();
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      HttpResponse httpResponse = new HttpResponse(HttpStatus.BAD_REQUEST.value(),
              HttpStatus.BAD_REQUEST,
              fieldError.getField(),
              fieldError.getDefaultMessage());

      httpResponses.add(httpResponse);
    }

    return ResponseEntity.badRequest().body(httpResponses);
  }

  // any other unhandled exceptions will be handled by this handler
  @ExceptionHandler(Exception.class)
  public ResponseEntity<HttpResponse> internalServerErrorException() {
    return generateHttpResponse(INTERNAL_SERVER_ERROR, ExceptionMessages.INTERNAL_SERVER_ERROR.getMsg());
  }

  private ResponseEntity<HttpResponse> generateHttpResponse(HttpStatus httpStatus, String msg) {
    return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
            httpStatus.getReasonPhrase(), msg), httpStatus);
  }
}
