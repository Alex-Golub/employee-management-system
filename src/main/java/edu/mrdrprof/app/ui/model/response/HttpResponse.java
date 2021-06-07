package edu.mrdrprof.app.ui.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * Custom http response object
 *
 * @author Alex Golub
 * @since 07-Jun-21, 9:10 PM
 */
@Getter
@Setter
public class HttpResponse {
  @JsonFormat(shape = JsonFormat.Shape.STRING,
              pattern = "dd-MMM-yyyy HH:mm:ss",
              timezone = "Asia/Jerusalem")
  private Date timeStamp;
  private int httpStatusCode;
  private HttpStatus httpStatus;
  private String reason;
  private String message;

  public HttpResponse(int httpStatusCode, HttpStatus httpStatus,
                      String reason, String message) {
    this.timeStamp = new Date();
    this.httpStatusCode = httpStatusCode;
    this.httpStatus = httpStatus;
    this.reason = reason;
    this.message = message;
  }
}
