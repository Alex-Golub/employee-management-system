package edu.mrdrprof.app.service.impl;

import edu.mrdrprof.app.io.entity.Address;
import edu.mrdrprof.app.repository.AddressRepository;
import edu.mrdrprof.app.service.AddressService;
import edu.mrdrprof.app.shared.AddressDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alex Golub
 * @since 08-Jun-21, 1:21 PM
 */
@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
  private final AddressRepository addressRepository;
  private final ModelMapper mapper;

  @Override
  public AddressDto getAddress(String addressId) {
    Address address = addressRepository.findAddressByPublicId(addressId);
    if (address == null)
      throw new RuntimeException("Address not found"); // FIXME

    return mapper.map(address, AddressDto.class);
  }

  @Override
  public List<AddressDto> getAddresses(String empId) {
    List<Address> addresses = addressRepository.findAddressesByEmployee_PublicId(empId);
    if (addresses == null)
      throw new RuntimeException("No address found"); // FIXME

    return mapper.map(addresses, new TypeToken<List<AddressDto>>() {}.getType());
  }
}
