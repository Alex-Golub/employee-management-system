package edu.mrdrprof.app.service.impl;

import edu.mrdrprof.app.aspects.annotations.PerformanceLogger;
import edu.mrdrprof.app.exceptions.model.EmployeeExistsException;
import edu.mrdrprof.app.exceptions.model.NotExistsException;
import edu.mrdrprof.app.io.entity.*;
import edu.mrdrprof.app.repository.*;
import edu.mrdrprof.app.service.EmployeeService;
import edu.mrdrprof.app.shared.*;
import edu.mrdrprof.app.shared.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static edu.mrdrprof.app.exceptions.ExceptionMessages.*;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 9:45 PM
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
  private final EmployeeRepository employeeRepository;
  private final GeneralDetailsRepository generalDetailsRepository;
  private final AddressRepository addressRepository;
  private final ChildRepository childRepository;
  private final SpouseRepository spouseRepository;
  private final Utils utils;
  private final ModelMapper mapper = new ModelMapper();

  @Override
  public EmployeeDto createEmployee(EmployeeDto employeeDto) {
    GeneralDetails emailAndSsn = generalDetailsRepository
            .findGeneralDetailsByEmailAndSsn(employeeDto.getGeneralDetails().getEmail(),
                    employeeDto.getGeneralDetails().getSsn());
    if (emailAndSsn != null) {
      throw new EmployeeExistsException(String.format(EMPLOYEE_EXISTS_BY_EMAIL_SSN.getMsg(),
              emailAndSsn.getEmail(), emailAndSsn.getSsn()));
    }

    Employee entity = employeeDtoToNewEmployeeEntity(employeeDto);
    Employee newEmployee = employeeRepository.save(entity);

    return mapper.map(newEmployee, EmployeeDto.class);
  }

  @Override
  public List<EmployeeDto> getEmployees(int page, int limit) { // TODO test
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

    throw new NotExistsException(String.format(EMPLOYEE_NOT_EXISTS.getMsg(), empId));
  }

  @Override
  @PerformanceLogger
  public EmployeeDto updateEmployee(String empId, EmployeeDto employeeDto) {
    patchGeneralDetails(empId, employeeDto.getGeneralDetails());
    patchSpouse(empId, employeeDto.getSpouse());

    Employee employee = getEmployee(empId);
    updateAddressesList(employeeDto.getAddresses(), employee);
    updateChildrenList(employeeDto.getChildren(), employee);

    return mapper.map(employee, EmployeeDto.class);
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
  public AddressDto patchAddress(String empId, String addressId, AddressDto addressDto) {
    Address foundAddress = getEmployee(empId).getAddresses()
            .stream()
            .filter(address -> address.getPublicId().equals(addressId))
            .findFirst()
            .orElseThrow(() -> new NotExistsException(String.format(ADDRESS_NOT_EXISTS.getMsg(), addressId)));

    BeanUtils.copyProperties(addressDto, foundAddress, "id", "employee", "publicId");
    Address updatedAddress = addressRepository.save(foundAddress);

    return mapper.map(updatedAddress, AddressDto.class);
  }

  @Override
  public ChildDto patchChild(String empId, String childId, ChildDto childDto) {
    Child foundChild = getEmployee(empId).getChildren()
            .stream()
            .filter(child -> child.getPublicId().equals(childId))
            .findFirst()
            .orElseThrow(() -> new NotExistsException(String.format(CHILD_NOT_EXISTS.getMsg(), childId)));

    BeanUtils.copyProperties(childDto, foundChild, "id", "employee", "publicId");
    Child updatedChild = childRepository.save(foundChild);

    return mapper.map(updatedChild, ChildDto.class);
  }

  @Override
  public void deleteEmployee(String empId) {
    Employee employee = getEmployee(empId);
    employeeRepository.deleteByPublicId(employee.getPublicId());
  }

  @Override
  public GeneralDetailsDto getEmployeeGeneralDetails(String detailsId) {
    GeneralDetails details = generalDetailsRepository.findGeneralDetailsByPublicId(detailsId);
    if (details == null)
      throw new NotExistsException(String.format(DETAILS_NOT_EXISTS.getMsg(), detailsId));

    return mapper.map(details, GeneralDetailsDto.class);
  }

  @Override
  public SpouseDto getSpouse(String spouseId) {
    Spouse spouse = spouseRepository.findSpouseByPublicId(spouseId);
    if (spouse == null)
      throw new NotExistsException(String.format(SPOUSE_NOT_EXISTS.getMsg(), spouseId));

    return mapper.map(spouse, SpouseDto.class);
  }

  @Override
  public AddressDto getAddress(String addressId) {
    Address address = addressRepository.findAddressByPublicId(addressId);
    if (address == null)
      throw new NotExistsException(String.format(ADDRESS_NOT_EXISTS.getMsg(), addressId));

    return mapper.map(address, AddressDto.class);
  }

  @Override
  public List<AddressDto> getAddresses(String empId) {
    return mapper.map(getEmployee(empId).getAddresses(), new TypeToken<List<AddressDto>>() {}.getType());
  }

  @Override
  public ChildDto getChild(String childId) {
    Child child = childRepository.findChildByPublicId(childId);
    if (child == null)
      throw new NotExistsException(String.format(CHILD_NOT_EXISTS.getMsg(), childId));

    return mapper.map(child, ChildDto.class);
  }

  @Override
  public List<ChildDto> getChildren(String empId) {
    return mapper.map(getEmployee(empId).getChildren(), new TypeToken<List<ChildDto>>() {}.getType());
  }

  private Employee employeeDtoToNewEmployeeEntity(EmployeeDto empDto) {
    Employee newEmployee = new Employee();

    GeneralDetails generalDetails = mapper.map(empDto.getGeneralDetails(), GeneralDetails.class);
    generalDetails.setEmployee(newEmployee); // bind newEmployee with this generalDetails
    generalDetails.setPublicId(utils.generatePublicId());
    newEmployee.setGeneralDetails(generalDetails);

    Spouse spouse = mapper.map(empDto.getSpouse(), Spouse.class);
    spouse.setEmployee(newEmployee);
    spouse.setPublicId(utils.generatePublicId());
    newEmployee.setSpouse(spouse);

    List<Address> addressList = mapper.map(empDto.getAddresses(), new TypeToken<List<Address>>() {
    }.getType());
    for (Address address : addressList) {
      address.setEmployee(newEmployee);
      address.setPublicId(utils.generatePublicId());
    }
    newEmployee.setAddresses(addressList);

    List<Child> childList = mapper.map(empDto.getChildren(), new TypeToken<List<Child>>() {
    }.getType());
    for (Child child : childList) {
      child.setEmployee(newEmployee);
      child.setPublicId(utils.generatePublicId());
    }
    newEmployee.setChildren(childList);

    newEmployee.setPublicId(utils.generatePublicId());
    return newEmployee;
  }
}
