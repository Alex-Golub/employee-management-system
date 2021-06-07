package edu.mrdrprof.app.service.impl;

import edu.mrdrprof.app.exceptions.ExceptionMessages;
import edu.mrdrprof.app.exceptions.model.AddressNotExistsException;
import edu.mrdrprof.app.exceptions.model.ChildNotExistsException;
import edu.mrdrprof.app.exceptions.model.EmployeeExistsException;
import edu.mrdrprof.app.exceptions.model.EmployeeNotExistsException;
import edu.mrdrprof.app.io.entity.*;
import edu.mrdrprof.app.repository.*;
import edu.mrdrprof.app.service.EmployeeService;
import edu.mrdrprof.app.shared.*;
import edu.mrdrprof.app.shared.utils.Utils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 9:45 PM
 */
@Service
@AllArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
  private final EmployeeRepository employeeRepository;
  private final GeneralDetailsRepository generalDetailsRepository;
  private final AddressRepository addressRepository;
  private final ChildRepository childRepository;
  private final SpouseRepository spouseRepository;
  private final Utils utils;
  private final ModelMapper mapper;

  @Override
  public EmployeeDto createEmployee(EmployeeDto employeeDto) {
    GeneralDetails emailAndSsn = generalDetailsRepository
            .findGeneralDetailsByEmailAndSsn(employeeDto.getGeneralDetails().getEmail(),
                    employeeDto.getGeneralDetails().getSsn());
    if (emailAndSsn != null) {
      throw new EmployeeExistsException(ExceptionMessages.EMPLOYEE_EXISTS_BY_EMAIL_SSN.getMsg());
    }

    Employee entity = utils.employeeDtoToNewEmployeeEntity(employeeDto);
    Employee newEmployee = employeeRepository.save(entity);

    return mapper.map(newEmployee, EmployeeDto.class);
  }

  @Override
  public List<EmployeeDto> getEmployees(int page, int limit) {
    if (page > 0) page--; // offset page to start at index 1 rather the 0 based

    return employeeRepository.findAll(PageRequest.of(page, limit))
            .stream()
            .map(emp -> mapper.map(emp, EmployeeDto.class))
            .collect(Collectors.toList());
  }

  @Override
  public EmployeeDto getEmployeeById(String empId) {
    return mapper.map(getEmployee(empId), EmployeeDto.class);
  }

  private Employee getEmployee(String empId) {
    Employee employee = employeeRepository.findEmployeeByPublicId(empId);
    if (employee != null) {
      return employee;
    }

    throw new EmployeeNotExistsException(ExceptionMessages.EMPLOYEE_NOT_EXISTS.getMsg());
  }

  @Override
  public EmployeeDto updateEmployee(String empId, EmployeeDto employeeDto) {
    patchGeneralDetails(empId, employeeDto.getGeneralDetails());
    patchSpouse(empId, employeeDto.getSpouse());

    Employee employee = getEmployee(empId);
    updateAddressesList(employeeDto.getAddresses(), employee);
    updateChildrenList(employeeDto.getChildren(), employee);

    return mapper.map(employee, EmployeeDto.class);
  }

  private void updateAddressesList(List<AddressDto> addressDtoList, Employee employee) {
    // remove all previous addresses of this employee
    addressRepository.deleteInBatch(employee.getAddresses());
    employee.getAddresses().clear();

    for (AddressDto addressDto : addressDtoList) {
      Address address = new Address();
      BeanUtils.copyProperties(addressDto, address);
      address.setEmployee(employee);
      address.setPublicId(utils.generatePublicId());
      employee.getAddresses().add(address);
    }
  }

  private void updateChildrenList(List<ChildDto> childDtoList, Employee employee) {
    childRepository.deleteInBatch(employee.getChildren());
    employee.getChildren().clear();

    for (ChildDto childDto : childDtoList) {
      Child child = new Child();
      BeanUtils.copyProperties(childDto, child);
      child.setEmployee(employee);
      child.setPublicId(utils.generatePublicId());
      employee.getChildren().add(child);
    }
  }

  @Override
  public GeneralDetailsDto patchGeneralDetails(String empId, GeneralDetailsDto detailsDto) {
    GeneralDetails generalDetails = getEmployee(empId).getGeneralDetails();
    BeanUtils.copyProperties(detailsDto, generalDetails, "id", "employee", "publicId");
    GeneralDetails newDetails = generalDetailsRepository.save(generalDetails);

    return mapper.map(newDetails, GeneralDetailsDto.class);
  }

  @Override
  public SpouseDto patchSpouse(String empId, SpouseDto spouseDto) {
    Spouse spouse = getEmployee(empId).getSpouse();
    BeanUtils.copyProperties(spouseDto, spouse, "id", "employee", "publicId");
    Spouse patchedSpouse = spouseRepository.save(spouse);

    return mapper.map(patchedSpouse, SpouseDto.class);
  }

  @Override
  public AddressDto patchAddress(String empId, String addressId, AddressDto addressDto) {
    Address address = addressRepository.findAddressByPublicId(addressId);
    if (address == null) {
      throw new AddressNotExistsException(ExceptionMessages.ADDRESS_NOT_FOUND.getMsg());
    }

    BeanUtils.copyProperties(addressDto, address, "id", "employee", "publicId");
    Address updatedAddress = addressRepository.save(address);

    return mapper.map(updatedAddress, AddressDto.class);
  }

  @Override
  public ChildDto patchChild(String empId, String childId, ChildDto childDto) {
    Child child = childRepository.findChildByPublicId(childId);
    if (child == null) {
      throw new ChildNotExistsException(ExceptionMessages.CHILD_NOT_FOUND.getMsg());
    }

    BeanUtils.copyProperties(childDto, child, "id", "employee", "publicId");
    Child updatedChild = childRepository.save(child);

    return mapper.map(updatedChild, ChildDto.class);
  }

  @Override
  public void deleteEmployee(String empId) {
    employeeRepository.deleteByPublicId(empId);
  }
}
