package edu.mrdrprof.app.ui.controller;

import edu.mrdrprof.app.exceptions.AppExceptionHandler;
import edu.mrdrprof.app.service.AddressService;
import edu.mrdrprof.app.service.EmployeeService;
import edu.mrdrprof.app.service.GeneralDetailsService;
import edu.mrdrprof.app.service.SpouseService;
import edu.mrdrprof.app.service.impl.ChildService;
import edu.mrdrprof.app.shared.*;
import edu.mrdrprof.app.ui.model.request.*;
import edu.mrdrprof.app.ui.model.response.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
  private final GeneralDetailsService generalDetailsService;
  private final SpouseService spouseService;
  private final AddressService addressService;
  private final ChildService childService;
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
  public EntityModel<SingleEmployeeRest> getEmployee(@PathVariable String empId) {
    EmployeeDto employeeDto = employeeService.getEmployeeById(empId);
    SingleEmployeeRest singleEmployeeRest = mapper.map(employeeDto, SingleEmployeeRest.class);

    return EntityModel.of(singleEmployeeRest,
            generalDetailsLink(empId, employeeDto.getGeneralDetails().getPublicId()),
            spouseLink(empId, employeeDto.getSpouse().getPublicId()),
            addressesLink(empId),
            childrenLink(empId));
  }

  /**
   * http://localhost:{port#}/{context-path}/employee?page=1&limit=5
   * Return list of employees by the amount specified by the query string
   * e.g. page = 1, limit = 2, two employee entries per page
   */
  @GetMapping
  public CollectionModel<SingleEmployeeRest> getListOfEmployees(
          @RequestParam(value = "page", defaultValue = "0") int page,
          @RequestParam(value = "limit", defaultValue = "5") int limit) {
    List<EmployeeDto> employeeDtos = employeeService.getEmployees(page, limit);
    List<SingleEmployeeRest> singleEmployeeRests = mapper.map(employeeDtos, new TypeToken<List<SingleEmployeeRest>>() {
    }.getType());

    for (SingleEmployeeRest employeeRest : singleEmployeeRests) {
      employeeRest.add(
              WebMvcLinkBuilder.linkTo(
                      WebMvcLinkBuilder.methodOn(getClass())
                              .getEmployee(employeeRest.getPublicId()))
                      .withSelfRel()
      );
    }

    return CollectionModel.of(singleEmployeeRests);
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
    GeneralDetailsDto detailsDto = mapper.map(detailsRequestModel, GeneralDetailsDto.class);
    GeneralDetailsDto detailsDtoPatched = employeeService.patchGeneralDetails(empId, detailsDto);

    return mapper.map(detailsDtoPatched, GeneralDetailsRest.class);
  }

  /**
   * http://localhost:{port#}/{context-path}/employee/{empId}/spouse
   */
  @PatchMapping(path = "/{empId}/spouse")
  public SpouseRest patchSpouse(@PathVariable String empId,
                                @RequestBody SpouseRequestModel spouseRequestModel) {
    SpouseDto spouseDto = mapper.map(spouseRequestModel, SpouseDto.class);
    SpouseDto spouseDtoPatched = employeeService.patchSpouse(empId, spouseDto);

    return mapper.map(spouseDtoPatched, SpouseRest.class);
  }

  /**
   * http://localhost:{port#}/{context-path}/employees/{empId}/address
   */
  @PatchMapping(path = "/{empId}/address/{addressId}")
  public AddressRest patchAddress(@PathVariable String empId, @PathVariable String addressId,
                                  @RequestBody AddressRequestModel addressRequestModel) {
    AddressDto addressDto = mapper.map(addressRequestModel, AddressDto.class);
    AddressDto addressDtoPatched = employeeService.patchAddress(empId, addressId, addressDto);

    return mapper.map(addressDtoPatched, AddressRest.class);
  }

  /**
   * http://localhost:{port#}/{context-path}/employees/{empId}/child/{childId}
   */
  @PatchMapping(path = "/{empId}/child/{childId}")
  public ChildRest patchChild(@PathVariable String empId, @PathVariable String childId,
                              @RequestBody ChildRequestModel childRequestModel) {
    ChildDto childDto = mapper.map(childRequestModel, ChildDto.class);
    ChildDto childDtoPatched = employeeService.patchChild(empId, childId, childDto);

    return mapper.map(childDtoPatched, ChildRest.class);
  }

  @DeleteMapping(path = "/{empId}")
  public void deleteEmployee(@PathVariable String empId) {
    employeeService.deleteEmployee(empId);
  }

  /**
   * http://localhost:{port#}/{context-path}/employee/{empId}/general-details/{detailsId}
   */
  @GetMapping(path = "/{empId}/general-details/{detailsId}")
  public EntityModel<GeneralDetailsRest> getEmployeeGeneralDetails(@PathVariable String empId,
                                                                   @PathVariable String detailsId) {
    GeneralDetailsDto detailsDto = generalDetailsService.getEmployeeGeneralDetails(detailsId);
    GeneralDetailsRest detailsRest = mapper.map(detailsDto, GeneralDetailsRest.class);

    return EntityModel.of(detailsRest, employeeLink(empId));
  }

  /**
   * http://localhost:{port#}/{context-path}/employee/{empId}/spouse/{spouseId}
   */
  @GetMapping(path = "/{empId}/spouse/{spouseId}")
  public EntityModel<SpouseRest> getEmployeeSpouseDetails(@PathVariable String empId,
                                                          @PathVariable String spouseId) {
    SpouseDto spouseDto = spouseService.getSpouse(spouseId);
    SpouseRest spouseRest = mapper.map(spouseDto, SpouseRest.class);

    return EntityModel.of(spouseRest, employeeLink(empId));
  }

  /**
   * http://localhost:{port#}/{context-path}/employee/{empId}/address/{addressId}
   */
  @GetMapping(path = "/{empId}/address/{addressId}")
  public EntityModel<AddressRest> getEmployeeAddress(@PathVariable String empId,
                                                     @PathVariable String addressId) {
    AddressDto addressDto = addressService.getAddress(addressId);
    AddressRest addressRest = mapper.map(addressDto, AddressRest.class);

    return EntityModel.of(addressRest, employeeLink(empId), addressesLink(empId));
  }

  /**
   * http://localhost:{port#}/{context-path}/employee/{empId}/addresses
   */
  @GetMapping(path = "/{empId}/addresses")
  public CollectionModel<AddressRest> getEmployeeAddresses(@PathVariable String empId) {
    List<AddressDto> addressDto = addressService.getAddresses(empId);
    List<AddressRest> addressRest = mapper.map(addressDto, new TypeToken<List<AddressRest>>() {
    }.getType());

    // add self relation link for each address
    for (AddressRest address : addressRest) {
      address.add(
              WebMvcLinkBuilder.linkTo(
                      WebMvcLinkBuilder.methodOn(getClass())
                              .getEmployeeAddress(empId, address.getPublicId())
              ).withSelfRel()
      );
    }

    return CollectionModel.of(addressRest, employeeLink(empId));
  }

  /**
   * http://localhost:{port#}/{context-path}/employee/{empId}/child/{childId}
   */
  @GetMapping(path = "/{empId}/child/{childId}")
  public EntityModel<ChildRest> getEmployeeChild(@PathVariable String empId,
                                                 @PathVariable String childId) {
    ChildDto childDto = childService.getChild(childId);
    ChildRest childRest = mapper.map(childDto, ChildRest.class);

    return EntityModel.of(childRest, employeeLink(empId));
  }

  /**
   * http://localhost:{port#}/{context-path}/employee/{empId}/children
   */
  @GetMapping(path = "/{empId}/children")
  public CollectionModel<ChildRest> getChildren(@PathVariable String empId) {
    List<ChildDto> childDtoList = childService.getChildren(empId);
    List<ChildRest> childRestList = mapper.map(childDtoList, new TypeToken<List<ChildRest>>() {
    }.getType());

    for (ChildRest childRest : childRestList) {
      childRest.add(
              WebMvcLinkBuilder.linkTo(
                      WebMvcLinkBuilder.methodOn(getClass())
                              .getEmployeeChild(empId, childRest.getPublicId())
              ).withSelfRel()
      );
    }

    return CollectionModel.of(childRestList, employeeLink(empId));
  }

  // helper methods to build links
  private Link generalDetailsLink(String empId, String detailsId) {
    return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(getClass())
                    .getEmployeeGeneralDetails(empId, detailsId))
            .withRel("general-details");
  }

  private Link spouseLink(String empId, String spouseId) {
    return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(getClass())
                    .getEmployeeSpouseDetails(empId, spouseId))
            .withRel("spouse-details");
  }

  private Link employeeLink(String empId) {
    return WebMvcLinkBuilder.linkTo(getClass()).slash(empId).withRel("employee");
  }

  private Link addressesLink(String empId) {
    return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(getClass())
                    .getEmployeeAddresses(empId))
            .withRel("addresses");
  }

  private Link childrenLink(String empId) {
    return WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(getClass())
                    .getChildren(empId))
            .withRel("children");
  }
}
