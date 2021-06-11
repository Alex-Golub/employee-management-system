package edu.mrdrprof.app.io.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alex Golub
 * @since 11-Jun-21, 9:51 PM
 */
public enum Sex {
  @JsonProperty("Male") MALE,
  @JsonProperty("Female") FEMALE
}
