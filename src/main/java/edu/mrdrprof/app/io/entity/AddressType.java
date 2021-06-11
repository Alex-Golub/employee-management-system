package edu.mrdrprof.app.io.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alex Golub
 * @since 11-Jun-21, 10:20 PM
 */
public enum AddressType {
  @JsonProperty("Shipping") SHIPPING,
  @JsonProperty("Billing") BILLING,
  @JsonProperty("Living") LIVING,
}
