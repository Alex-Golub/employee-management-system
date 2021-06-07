package edu.mrdrprof.app.ui.controller;

import edu.mrdrprof.app.exceptions.AppExceptionHandler;
import edu.mrdrprof.app.service.EmployeeService;
import edu.mrdrprof.app.shared.*;
import edu.mrdrprof.app.ui.model.request.*;
import edu.mrdrprof.app.ui.model.response.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 9:46 PM
 */
@RestController
@RequestMapping(path = "/employee")
@AllArgsConstructor
public class EmployeeController extends AppExceptionHandler {
  private final EmployeeService employeeService;
  private final ModelMapper mapper;

  /**
   * http://localhost:{port#}/{context-path}/employees
   */
  @PostMapping
  public EmployeeRest createEmployee(@RequestBody EmployeeRequestModel employeeRequestModel) {
    EmployeeDto empDto = mapper.map(employeeRequestModel, EmployeeDto.class);
    EmployeeDto newEmployee = employeeService.createEmployee(empDto);
    return mapper.map(newEmployee, EmployeeRest.class);
  }

  /**
   * http://localhost:{port#}/{context-path}/employee/{empId}
   * Return single employee by its public id
   */
  @GetMapping(path = "/{empId}")
  public EmployeeRest getEmployee(@PathVariable String empId) { // TODO: return employee generalDetails with HATEOAS
    return mapper.map(employeeService.getEmployeeById(empId), EmployeeRest.class);
  }

  /**
   * http://localhost:{port#}/{context-path}/employee?page=1&limit=5
   * Return list of employees by the amount specified by the query string
   * e.g. page = 1, limit = 2, two employee entries per page
   */
  @GetMapping
  public List<EmployeeRest> getListOfEmployees(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "limit", defaultValue = "5") int limit) {
    return mapper.map(employeeService.getEmployees(page, limit),
            new TypeToken<List<EmployeeRest>>() {
            }.getType());
  }

  /**
   * http://localhost:{port#}/{context-path}/employee/{empId}
   * Entirely replace existing employee info
   */
  @PutMapping(path = "/{empId}")
  public EmployeeRest updateEmployee(@PathVariable String empId,
                                     @RequestBody EmployeeRequestModel requestModel) {
    EmployeeDto empDto = mapper.map(requestModel, EmployeeDto.class);
    EmployeeDto updatedEmployee = employeeService.updateEmployee(empId, empDto);
    return mapper.map(updatedEmployee, EmployeeRest.class);
  }

  /**
   * http://localhost:{port#}/{context-path}/employee/{empId}/general-details
   */
  @PatchMapping(path = "/{empId}/general-details")
  public GeneralDetailsRest patchGeneralDetails(@PathVariable String empId,
                                                @RequestBody GeneralDetailsRequestModel detailsRequestModel) {
    GeneralDetailsDto generalDetailsDto =
            employeeService.patchGeneralDetails(empId, mapper.map(detailsRequestModel, GeneralDetailsDto.class));
    return mapper.map(generalDetailsDto, GeneralDetailsRest.class);
  }

  @PatchMapping(path = "/{empId}/spouse")
  public SpouseRest patchSpouse(@PathVariable String empId,
                                @RequestBody SpouseRequestModel spouseRequestModel) {
    SpouseDto spouseDto = employeeService.patchSpouse(empId, mapper.map(spouseRequestModel, SpouseDto.class));
    return mapper.map(spouseDto, SpouseRest.class);
  }

  /**
   * http://localhost:{port#}/{context-path}/employees/{empId}/address
   */
  @PatchMapping(path = "/{empId}/address/{addressId}")
  public AddressRest patchAddress(@PathVariable String empId, @PathVariable String addressId,
                                  @RequestBody AddressRequestModel addressRequestModel) {
    AddressDto addressDto = mapper.map(addressRequestModel, AddressDto.class);
    return mapper.map(employeeService.patchAddress(empId, addressId, addressDto), AddressRest.class);
  }

  /**
   * http://localhost:{port#}/{context-path}/employees/{empId}/child/{childId}
   */
  @PatchMapping(path = "/{empId}/child/{childId}")
  public ChildRest patchChild(@PathVariable String empId, @PathVariable String childId,
                              @RequestBody ChildRequestModel childRequestModel) {
    ChildDto childDto = mapper.map(childRequestModel, ChildDto.class);
    return mapper.map(employeeService.patchChild(empId, childId, childDto), ChildRest.class);
  }

  @DeleteMapping(path = "/{empId}")
  public void deleteEmployee(@PathVariable String empId) {
    employeeService.deleteEmployee(empId);
  }
}
