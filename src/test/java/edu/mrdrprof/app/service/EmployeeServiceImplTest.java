package edu.mrdrprof.app.service;

import edu.mrdrprof.app.exceptions.model.EmployeeExistsException;
import edu.mrdrprof.app.exceptions.model.NotExistsException;
import edu.mrdrprof.app.io.entity.Address;
import edu.mrdrprof.app.io.entity.Employee;
import edu.mrdrprof.app.io.entity.GeneralDetails;
import edu.mrdrprof.app.io.entity.Spouse;
import edu.mrdrprof.app.repository.*;
import edu.mrdrprof.app.service.impl.EmployeeServiceImpl;
import edu.mrdrprof.app.shared.AddressDto;
import edu.mrdrprof.app.shared.EmployeeDto;
import edu.mrdrprof.app.shared.GeneralDetailsDto;
import edu.mrdrprof.app.shared.SpouseDto;
import edu.mrdrprof.app.shared.utils.Utils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Alex Golub
 * @since 10-Jun-21, 11:20 AM
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
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
  final String EMPLOYEE_PUBLIC_ID = "P75ffeWvS1i887jElo0b";
  final String DETAILS_PUBLIC_ID = "p3Z1TB2osl9hOhfDF2lJ";
  final String ADDRESS_1_PUBLIC_ID = "xM4zLIIiPVhZBp3asy7W";
  final String ADDRESS_2_PUBLIC_ID = "rbbG1o5FzsHinCyH8V1r";
  final String CHILD_1_PUBLIC_ID = "OVc2OPUzbnrBDoZvmit0";
  final String CHILD_2_PUBLIC_ID = "Y1wR8o7mVAlPRJCngnUB";

  @BeforeEach
  void setUp() {
    employee = Employee.builder()
            .id(1L)
            .publicId(EMPLOYEE_PUBLIC_ID)
            .generalDetails(getGeneralDetails())
            .spouse(getSpouse())
            .addresses(Lists.list(getAddress1(), getAddress2()))
            .children(Lists.list()) // TODO
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
    when(generalDetailsRepository.findGeneralDetailsByEmailAndSsn(anyString(), anyString())).thenReturn(employee.getGeneralDetails());
    assertThrows(EmployeeExistsException.class,
            () -> employeeService.createEmployee(getEmployeeDto()));
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
    assertEquals(employee.getGeneralDetails().getFirstName(), updatedDetails.getFirstName());
    assertEquals("alex@email.com", updatedDetails.getEmail());
    assertEquals(employee.getGeneralDetails().getPublicId(), updatedDetails.getPublicId());
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
  }

  @Test
  void shouldPatchSingleAddress() {
    fail();
  }

  @Test
  void shouldPatchSingleChild() {
//    fail();
  }

  @Test
  void shouldDeleteEmployee() {
//    fail();
  }

  @Test
  void shouldPatchChild() {
//    fail();
  }

  private EmployeeDto getEmployeeDto() {
    return EmployeeDto.builder()
            .generalDetails(getGeneralDetailsDto())
            .spouse(getSpouseDto())
            .addresses(Lists.list(getAddressDto1(), getAddressDto2()))
            .children(Lists.list()) // TODO
            .build();
  }

  private AddressDto getAddressDto1() {
    return AddressDto.builder()
            .publicId(ADDRESS_1_PUBLIC_ID)
            .city("Tel-Aviv")
            .country("Israel")
            .streetName("Dizengoff")
            .postalCode("42")
            .type("Shipping")
            .build();
  }

  private AddressDto getAddressDto2() {
    return AddressDto.builder()
            .publicId(ADDRESS_2_PUBLIC_ID)
            .city("Haifa")
            .country("Israel")
            .streetName("Allenby")
            .postalCode("24")
            .type("Billing")
            .build();
  }

  private Address getAddress1() {
    return Address.builder()
            .publicId(ADDRESS_1_PUBLIC_ID)
            .city("Tel-Aviv")
            .country("Israel")
            .streetName("Dizengoff")
            .postalCode("42")
            .type("Shipping")
            .build();
  }

  private Address getAddress2() {
    return Address.builder()
            .publicId(ADDRESS_2_PUBLIC_ID)
            .city("Haifa")
            .country("Israel")
            .streetName("Allenby")
            .postalCode("24")
            .type("Billing")
            .build();
  }

  private SpouseDto getSpouseDto() {
    SpouseDto spouseDto = new SpouseDto();
    BeanUtils.copyProperties(getSpouse(), spouseDto);
    return spouseDto;
  }

  private Spouse getSpouse() {
    return Spouse.builder()
            .id(1L)
            .firstName("Jane")
            .lastName("Doe")
            .email("jane@email.com")
            .phoneNumber("987-654-321")
            .ssn("4321")
            .sex("Female")
            .build();
  }

  private GeneralDetailsDto getGeneralDetailsDto() {
    GeneralDetailsDto generalDetails = new GeneralDetailsDto();
    BeanUtils.copyProperties(getGeneralDetails(), generalDetails);
    return generalDetails;
  }

  private GeneralDetails getGeneralDetails() {
    return GeneralDetails.builder()
            .id(1L)
            .publicId(DETAILS_PUBLIC_ID)
            .firstName("Bob")
            .lastName("Doe")
            .email("bob@email.com")
            .phoneNumber("123-456-789")
            .ssn("1234")
            .sex("Male")
            .hireDate(new Date(LocalDate.of(2025, Month.JANUARY, 1).toEpochDay()))
            .build();
  }
}
