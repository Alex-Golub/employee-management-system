package edu.mrdrprof.app.service;

import edu.mrdrprof.app.shared.AddressDto;

import java.util.List;

/**
 * @author Alex Golub
 * @since 08-Jun-21, 1:20 PM
 */
public interface AddressService {
  AddressDto getAddress(String addressId);
  List<AddressDto> getAddresses(String empId);
}
