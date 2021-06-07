package edu.mrdrprof.app.shared.utils;

import edu.mrdrprof.app.io.entity.*;
import edu.mrdrprof.app.shared.EmployeeDto;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alex Golub
 * @since 07-Jun-21, 7:15 PM
 */
@Service
@AllArgsConstructor
public class Utils {
  private final ModelMapper mapper;
  private final int PUBLIC_ID_LENGTH = 20;

  public String generatePublicId() {
    return RandomStringUtils.randomAlphanumeric(PUBLIC_ID_LENGTH);
  }

  public Employee employeeDtoToNewEmployeeEntity(EmployeeDto empDto) {
    Employee newEmployee = new Employee();

    GeneralDetails generalDetails = mapper.map(empDto.getGeneralDetails(), GeneralDetails.class);
    generalDetails.setEmployee(newEmployee); // bind newEmployee with this generalDetails
    generalDetails.setPublicId(generatePublicId());
    newEmployee.setGeneralDetails(generalDetails);

    Spouse spouse = mapper.map(empDto.getSpouse(), Spouse.class);
    spouse.setEmployee(newEmployee);
    spouse.setPublicId(generatePublicId());
    newEmployee.setSpouse(spouse);

    List<Address> addressList = mapper.map(empDto.getAddresses(), new TypeToken<List<Address>>() {
    }.getType());
    for (Address address : addressList) {
      address.setEmployee(newEmployee);
      address.setPublicId(generatePublicId());
    }
    newEmployee.setAddresses(addressList);

    List<Child> childList = mapper.map(empDto.getChildren(), new TypeToken<List<Child>>() {
    }.getType());
    for (Child child : childList) {
      child.setEmployee(newEmployee);
      child.setPublicId(generatePublicId());
    }
    newEmployee.setChildren(childList);

    newEmployee.setPublicId(generatePublicId());
    return newEmployee;
  }
}
