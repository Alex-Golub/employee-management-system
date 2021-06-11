package edu.mrdrprof.app.shared.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Alex Golub
 * @since 07-Jun-21, 7:15 PM
 */
@Service
public class Utils {
  private final int PUBLIC_ID_LENGTH = 20;

  public String generatePublicId() {
    return RandomStringUtils.randomAlphanumeric(PUBLIC_ID_LENGTH);
  }
}
