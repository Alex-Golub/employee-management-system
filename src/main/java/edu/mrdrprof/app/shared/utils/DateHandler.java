package edu.mrdrprof.app.shared.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 11:04 PM
 */
public class DateHandler extends JsonDeserializer<Date> {
  @Override
  public Date deserialize(JsonParser parser, DeserializationContext context)
          throws IOException {
    String date = parser.getText();
    try {
      SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
      return format.parse(date);
    } catch (Exception e) {
      return null;
    }
  }
}
