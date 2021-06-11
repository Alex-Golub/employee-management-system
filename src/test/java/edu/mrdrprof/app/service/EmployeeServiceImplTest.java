package edu.mrdrprof.app.service;

import edu.mrdrprof.app.DataGenerator;
import edu.mrdrprof.app.exceptions.model.EmployeeExistsException;
import edu.mrdrprof.app.exceptions.model.NotExistsException;
import edu.mrdrprof.app.io.entity.*;
import edu.mrdrprof.app.repository.*;
import edu.mrdrprof.app.service.impl.EmployeeServiceImpl;
import edu.mrdrprof.app.shared.*;
import edu.mrdrprof.app.shared.utils.Utils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Alex Golub
 * @since 10-Jun-21, 11:20 AM
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest extends DataGenerator {
  @Mock
  private EmployeeRepository employeeRepository;
  @Mock
  private GeneralDetailsRepository generalDetailsRepository;
  @Mock
  private AddressRepository addressRepository;
  @Mock
  private ChildRepository childRepository;
  @Mock
  private SpouseRepository spouseRepository;
  @Mock
  private ModelMapper mapper;
  @Mock
  private Utils utils;
  @InjectMocks
  private EmployeeServiceImpl employeeService;

  Employee employee;

  @BeforeEach
  void setUp() {
    employee = Employee.builder()
            .id(1L)
            .publicId(EMPLOYEE_PUBLIC_ID)
            .generalDetails(getGeneralDetails())
            .spouse(getSpouse())
            .addresses(Lists.list(getAddress1(), getAddress2()))
            .children(Lists.list(getChild1(), getChild2()))
            .build();
  }

  @Test
  void shouldReturnEmployeeById() {
    when(employeeRepository.findEmployeeByPublicId(anyString())).thenReturn(employee);

    EmployeeDto employeeDto = employeeService.getEmployeeById(employee.getPublicId());

    assertNotNull(employeeDto);
    assertEquals(employee.getId(), employeeDto.getId());
  }

  @Test
  void shouldThrowIfNoEmployeeFoundById() {
    when(employeeRepository.findEmployeeByPublicId(anyString())).thenReturn(null);

    assertThrows(NotExistsException.class,
            () -> employeeService.getEmployeeById(employee.getPublicId()));
  }

  @Test
  void shouldCreateEmployee() {
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
    EmployeeDto employeeDto = getEmployeeDto();
    EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);

    assertNotNull(createdEmployee);
    assertEquals(employee.getPublicId(), createdEmployee.getPublicId());
    assertNotNull(createdEmployee.getGeneralDetails());
    assertEquals(employee.getGeneralDetails().getPublicId(), createdEmployee.getGeneralDetails().getPublicId());
    assertNotNull(createdEmployee.getAddresses());
    assertEquals(employee.getAddresses().size(), createdEmployee.getAddresses().size());

    verify(employeeRepository, times(1)).save(any(Employee.class));
  }

  @Test
  void shouldThrowIfEmployeeWithEmailAndSsnFound() {
    when(generalDetailsRepository.findGeneralDetailsByEmailAndSsn(anyString(), anyString()))
            .thenReturn(employee.getGeneralDetails());

    assertThrows(EmployeeExistsException.class,
            () -> employeeService.createEmployee(getEmployeeDto()));
  }

  @Test
  void shouldReturnListOfEmployees() {
    // TODO
  }

  @Test
  void shouldPatchGeneralDetails() {
    when(employeeRepository.findEmployeeByPublicId(anyString())).thenReturn(employee);
    when(generalDetailsRepository.save(any(GeneralDetails.class))).thenReturn(employee.getGeneralDetails());

    GeneralDetailsDto generalDetailsDto = getGeneralDetailsDto();
    generalDetailsDto.setFirstName("Alex");
    generalDetailsDto.setEmail("alex@email.com");

    GeneralDetailsDto updatedDetails = employeeService.patchGeneralDetails(anyString(), generalDetailsDto);

    assertNotNull(updatedDetails);
    assertEquals(updatedDetails.getFirstName(), employee.getGeneralDetails().getFirstName());
    assertEquals(updatedDetails.getEmail(), employee.getGeneralDetails().getEmail());
    assertEquals(updatedDetails.getPublicId(), employee.getGeneralDetails().getPublicId());

    verify(employeeRepository, times((1))).findEmployeeByPublicId((anyString()));
    verify(generalDetailsRepository, times(1)).save(any(GeneralDetails.class));
  }

  @Test
  void shouldThrowIfPatchingDetailsOfNotExistingEmployee() {
    when(employeeRepository.findEmployeeByPublicId(anyString())).thenReturn(null);

    assertThrows(NotExistsException.class,
            () -> employeeService.patchGeneralDetails(anyString(), getGeneralDetailsDto()));
  }

  @Test
  void shouldPatchSpouse() {
    when(employeeRepository.findEmployeeByPublicId(anyString())).thenReturn(employee);
    when(spouseRepository.save(any(Spouse.class))).thenReturn(employee.getSpouse());

    SpouseDto spouseDto = getSpouseDto();
    spouseDto.setFirstName("Nora");

    SpouseDto updatedSpouse = employeeService.patchSpouse(anyString(), spouseDto);

    assertNotNull(updatedSpouse);
    assertEquals(spouseDto.getFirstName(), updatedSpouse.getFirstName());

    verify(employeeRepository, times((1))).findEmployeeByPublicId((anyString()));
    verify(spouseRepository, times(1)).save(any(Spouse.class));
  }

  @Test
  void shouldThrowIfPatchingSpouseOfNotExistingEmployee() {
    when(employeeRepository.findEmployeeByPublicId(anyString())).thenReturn(null);

    assertThrows(NotExistsException.class,
            () -> employeeService.patchSpouse(anyString(), getSpouseDto()));
  }

  @Test
  void shouldPatchSingleAddress() {
    when(employeeRepository.findEmployeeByPublicId(anyString())).thenReturn(employee);
    when(addressRepository.save(any(Address.class))).thenReturn(employee.getAddresses().get(0));

    AddressDto addressDto = getAddressDto1();
    addressDto.setStreetName("Lost Ave.");
    addressDto.setCity("Heaven");
    AddressDto patchAddress = employeeService.patchAddress(employee.getPublicId(),
            addressDto.getPublicId(), addressDto);

    assertNotNull(patchAddress);
    assertEquals(addressDto.getStreetName(), employee.getAddresses().get(0).getStreetName());
    assertEquals(addressDto.getCity(), employee.getAddresses().get(0).getCity());

    verify(employeeRepository, times(1)).findEmployeeByPublicId(anyString());
    verify(addressRepository, times(1)).save(any(Address.class));
  }

  @Test
  void shouldThrowWhenPatchingNoneExistingAddress() {
    when(employeeRepository.findEmployeeByPublicId(anyString())).thenReturn(employee);

    AddressDto addressDto = getAddressDto1();
    addressDto.setStreetName("Lost Ave.");

    assertThrows(NotExistsException.class,
            () -> employeeService.patchAddress(employee.getPublicId(),
                    anyString(), addressDto));
  }

  @Test
  void shouldPatchSingleChild() {
    when(employeeRepository.findEmployeeByPublicId(anyString())).thenReturn(employee);
    when(childRepository.save(any(Child.class))).thenReturn(employee.getChildren().get(0));

    ChildDto childDto = getChildDto1();
    childDto.setFirstName("Brian");

    ChildDto patchChild = employeeService.patchChild(employee.getPublicId(),
            childDto.getPublicId(), childDto);

    assertNotNull(patchChild);
    assertEquals(childDto.getFirstName(), employee.getChildren().get(0).getFirstName());

    verify(employeeRepository, times(1)).findEmployeeByPublicId(anyString());
    verify(childRepository, times(1)).save(any(Child.class));
  }

  @Test
  void shouldThrowWhenPatchingNoneExistingChild() {
    when(employeeRepository.findEmployeeByPublicId(anyString())).thenReturn(employee);

    ChildDto childDto = getChildDto1();
    childDto.setFirstName("Brian");

    assertThrows(NotExistsException.class,
            () -> employeeService.patchChild(employee.getPublicId(), anyString(), childDto));
  }

  @Test
  void shouldDeleteEmployee() {
    employeeService.deleteEmployee(employee.getPublicId());
    verify(employeeRepository, times(1)).deleteByPublicId(anyString());
  }

  @Test
  void shouldReturnEmployeeGeneralDetails() {
    when(generalDetailsRepository.findGeneralDetailsByPublicId(any()))
            .thenReturn(employee.getGeneralDetails());

    GeneralDetailsDto generalDetails = employeeService.getEmployeeGeneralDetails(anyString());

    assertNotNull(generalDetails);
    assertEquals(employee.getGeneralDetails().getPublicId(), generalDetails.getPublicId());
    verify(generalDetailsRepository, times(1)).findGeneralDetailsByPublicId(anyString());
  }

  @Test
  void shouldThrowWhenGeneralDetailsNotFound() {
    when(generalDetailsRepository.findGeneralDetailsByPublicId(any())).thenReturn(null);

    assertThrows(NotExistsException.class,
            () -> employeeService.getEmployeeGeneralDetails(anyString()));
  }

  @Test
  void shouldReturnEmployeeSpouse() {
    when(spouseRepository.findSpouseByPublicId(any())).thenReturn(employee.getSpouse());

    SpouseDto spouseDto = employeeService.getSpouse(anyString());

    assertNotNull(spouseDto);
    assertEquals(employee.getSpouse().getPublicId(), spouseDto.getPublicId());
    verify(spouseRepository, times(1)).findSpouseByPublicId(anyString());
  }

  @Test
  void shouldThrowWhenSpouseNotFound() {
    when(spouseRepository.findSpouseByPublicId(any())).thenReturn(null);

    assertThrows(NotExistsException.class,
            () -> employeeService.getSpouse(anyString()));
  }

  @Test
  void shouldReturnEmployeeSingleAddress() {
    Address address = employee.getAddresses().get(0);
    when(addressRepository.findAddressByPublicId(anyString())).thenReturn(address);

    AddressDto addressDto = employeeService.getAddress(anyString());

    assertNotNull(addressDto);
    assertEquals(address.getPublicId(), addressDto.getPublicId());
    assertEquals(address.getCity(), addressDto.getCity());
    verify(addressRepository, times(1)).findAddressByPublicId(anyString());
  }

  @Test
  void shouldThrowWhenAddressNotFound() {
    when(addressRepository.findAddressByPublicId(any())).thenReturn(null);

    assertThrows(NotExistsException.class,
            () -> employeeService.getAddress(anyString()));
  }

  @Test
  void shouldReturnListOfAddresses() {
    when(employeeRepository.findEmployeeByPublicId(anyString())).thenReturn(employee);
    List<AddressDto> addressDtos = employeeService.getAddresses(anyString());

    assertNotNull(addressDtos);
    assertEquals(addressDtos.size(), employee.getAddresses().size());
  }

  @Test
  void shouldReturnSingleChild() {
    Child child = employee.getChildren().get(0);
    when(childRepository.findChildByPublicId(anyString())).thenReturn(child);

    ChildDto childDto = employeeService.getChild(anyString());

    assertNotNull(childDto);
    assertEquals(childDto.getPublicId(), childDto.getPublicId());
    assertEquals(childDto.getFirstName(), childDto.getFirstName());
    verify(childRepository, times(1)).findChildByPublicId(anyString());
  }

  @Test
  void shouldThrowWhenChildNotFound() {
    when(childRepository.findChildByPublicId(anyString())).thenReturn(null);

    assertThrows(NotExistsException.class,
            () -> employeeService.getChild(anyString()));
  }
}
