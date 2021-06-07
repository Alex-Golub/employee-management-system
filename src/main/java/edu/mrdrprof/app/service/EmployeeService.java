package edu.mrdrprof.app.service;

import edu.mrdrprof.app.shared.*;

import java.util.List;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 9:28 PM
 */
public interface EmployeeService {
  List<EmployeeDto> getEmployees(int page, int limit);

  EmployeeDto getEmployeeById(String empId);

  EmployeeDto createEmployee(EmployeeDto empDto);

  EmployeeDto updateEmployee(String empId, EmployeeDto empDto);

  GeneralDetailsDto patchGeneralDetails(String empId, GeneralDetailsDto detailsDto);

  SpouseDto patchSpouse(String empId, SpouseDto spouseDto);

  AddressDto patchAddress(String empId, String addressId, AddressDto addressDto);

  ChildDto patchChild(String empId, String childId, ChildDto childDto);

  void deleteEmployee(String empId);
}
