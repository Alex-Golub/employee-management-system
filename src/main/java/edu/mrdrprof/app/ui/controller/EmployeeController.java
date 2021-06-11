package edu.mrdrprof.app.ui.controller;

import edu.mrdrprof.app.service.EmployeeService;
import edu.mrdrprof.app.shared.*;
import edu.mrdrprof.app.ui.model.request.*;
import edu.mrdrprof.app.ui.model.response.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 9:46 PM
 */
@RestController
@RequestMapping(path = "/employee")
@AllArgsConstructor
public class EmployeeController {
  private final EmployeeService employeeService;
  private final ModelMapper mapper;

  // http://localhost:{port#}/{context-path}/employee
  @ApiOperation(value = "${EmployeeController.createEmployee.value}",
                notes = "${EmployeeController.createEmployee.notes}")
  @PostMapping
  public EmployeeRest createEmployee(
          @ApiParam(value = "All employee details as JSON", required = true)
          @RequestBody @Valid EmployeeRequestModel employeeRequestModel) {
    EmployeeDto empDto = mapper.map(employeeRequestModel, EmployeeDto.class);
    EmployeeDto newEmployee = employeeService.createEmployee(empDto);

    return mapper.map(newEmployee, EmployeeRest.class);
  }

  // http://localhost:{port#}/{context-path}/employee/{empId}
  @ApiOperation(value = "${EmployeeController.getEmployee.value}",
                notes = "${EmployeeController.getEmployee.notes}")
  @GetMapping(path = "/{empId}")
  public EntityModel<SingleEmployeeRest> getEmployee(
          @ApiParam(value = "Employee public id", example = "e.g. TmpTb4efBF9IqS46Zrio", required = true)
          @PathVariable String empId) {
    EmployeeDto employeeDto = employeeService.getEmployeeById(empId);
    SingleEmployeeRest singleEmployeeRest = mapper.map(employeeDto, SingleEmployeeRest.class);

    return EntityModel.of(singleEmployeeRest,
            generalDetailsLink(empId, employeeDto.getGeneralDetails().getPublicId()),
            spouseLink(empId, employeeDto.getSpouse().getPublicId()),
            addressesLink(empId),
            childrenLink(empId));
  }

  // http://localhost:{port#}/{context-path}/employee?page=1&limit=5
  // e.g. page = 1, limit = 2, two employee entries per page
  @ApiOperation(value = "${EmployeeController.getListOfEmployees.value}",
                notes = "${EmployeeController.getListOfEmployees.notes}")
  @GetMapping
  public CollectionModel<SingleEmployeeRest> getListOfEmployees(
          @ApiParam(value = "How many pages of employee entries to return")
          @RequestParam(value = "page", defaultValue = "0") int page,
          @ApiParam(value = "How many employees entries per-page to return")
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

  // http://localhost:{port#}/{context-path}/employee/{empId}
  @ApiOperation(value = "${EmployeeController.updateEmployee.value}",
                notes = "${EmployeeController.updateEmployee.notes}")
  @PutMapping(path = "/{empId}")
  public EmployeeRest updateEmployee(
          @ApiParam(value = "Employee public id", example = "e.g. TmpTb4efBF9IqS46Zrio", required = true)
          @PathVariable String empId,
          @ApiParam(value = "JSON with new employee values", required = true)
          @RequestBody @Valid EmployeeRequestModel requestModel) {
    EmployeeDto empDto = mapper.map(requestModel, EmployeeDto.class);
    EmployeeDto updatedEmployee = employeeService.updateEmployee(empId, empDto);

    return mapper.map(updatedEmployee, EmployeeRest.class);
  }

  // http://localhost:{port#}/{context-path}/employee/{empId}/general-details
  @ApiOperation(value = "${EmployeeController.patchGeneralDetails.value}",
                notes = "${EmployeeController.patchGeneralDetails.notes}")
  @PatchMapping(path = "/{empId}/general-details")
  public GeneralDetailsRest patchGeneralDetails(
          @ApiParam(value = "Employee public id", example = "e.g. TmpTb4efBF9IqS46Zrio", required = true)
          @PathVariable String empId,
          @ApiParam(value = "JSON with updated general details ", required = true)
          @RequestBody @Valid GeneralDetailsRequestModel detailsRequestModel) {
    GeneralDetailsDto detailsDto = mapper.map(detailsRequestModel, GeneralDetailsDto.class);
    GeneralDetailsDto detailsDtoPatched = employeeService.patchGeneralDetails(empId, detailsDto);

    return mapper.map(detailsDtoPatched, GeneralDetailsRest.class);
  }

  // http://localhost:{port#}/{context-path}/employee/{empId}/spouse
  @ApiOperation(value = "${EmployeeController.patchSpouse.value}",
                notes = "${EmployeeController.patchSpouse.notes}")
  @PatchMapping(path = "/{empId}/spouse")
  public SpouseRest patchSpouse(
          @ApiParam(value = "Employee public id", example = "e.g. TmpTb4efBF9IqS46Zrio", required = true)
          @PathVariable String empId,
          @ApiParam(value = "JSON with updated spouse details ", required = true)
          @RequestBody @Valid SpouseRequestModel spouseRequestModel) {
    SpouseDto spouseDto = mapper.map(spouseRequestModel, SpouseDto.class);
    SpouseDto spouseDtoPatched = employeeService.patchSpouse(empId, spouseDto);

    return mapper.map(spouseDtoPatched, SpouseRest.class);
  }

  // http://localhost:{port#}/{context-path}/employee/{empId}/address
  @ApiOperation(value = "${EmployeeController.patchAddress.value}",
                notes = "${EmployeeController.patchAddress.notes}")
  @PatchMapping(path = "/{empId}/address/{addressId}")
  public AddressRest patchAddress(
          @ApiParam(value = "Employee public id", example = "e.g. TmpTb4efBF9IqS46Zrio", required = true)
          @PathVariable String empId,
          @ApiParam(value = "Address public id", example = "e.g. dWgnpLpv7daEo3p8Cz3f", required = true)
          @PathVariable String addressId,
          @ApiParam(value = "JSON with updated address details ", required = true)
          @RequestBody @Valid AddressRequestModel addressRequestModel) {
    AddressDto addressDto = mapper.map(addressRequestModel, AddressDto.class);
    AddressDto addressDtoPatched = employeeService.patchAddress(empId, addressId, addressDto);

    return mapper.map(addressDtoPatched, AddressRest.class);
  }

  // http://localhost:{port#}/{context-path}/employee/{empId}/child/{childId}
  @ApiOperation(value = "${EmployeeController.patchChild.value}",
                notes = "${EmployeeController.patchChild.notes}")
  @PatchMapping(path = "/{empId}/child/{childId}")
  public ChildRest patchChild(
          @ApiParam(value = "Employee public id", example = "e.g. TmpTb4efBF9IqS46Zrio", required = true)
          @PathVariable String empId,
          @ApiParam(value = "Child public id", example = "e.g. Jv0aPkvQcG8D53hiRzM2", required = true)
          @PathVariable String childId,
          @ApiParam(value = "JSON with updated child details ", required = true)
          @RequestBody @Valid ChildRequestModel childRequestModel) {
    ChildDto childDto = mapper.map(childRequestModel, ChildDto.class);
    ChildDto childDtoPatched = employeeService.patchChild(empId, childId, childDto);

    return mapper.map(childDtoPatched, ChildRest.class);
  }

  // http://localhost:{port#}/{context-path}/employee/{empId}
  @ApiOperation(value = "${EmployeeController.deleteEmployee.value}",
                notes = "${EmployeeController.deleteEmployee.notes}")
  @DeleteMapping(path = "/{empId}")
  public void deleteEmployee(
          @ApiParam(value = "Employee public id", example = "e.g. TmpTb4efBF9IqS46Zrio", required = true)
          @PathVariable String empId) {
    employeeService.deleteEmployee(empId);
  }

  // http://localhost:{port#}/{context-path}/employee/{empId}/general-details/{detailsId}
  @ApiOperation(value = "${EmployeeController.getEmployeeGeneralDetails.value}",
                notes = "${EmployeeController.getEmployeeGeneralDetails.notes}")
  @GetMapping(path = "/{empId}/general-details/{detailsId}")
  public EntityModel<GeneralDetailsRest> getEmployeeGeneralDetails(
          @ApiParam(value = "Employee public id", example = "e.g. TmpTb4efBF9IqS46Zrio", required = true)
          @PathVariable String empId,
          @ApiParam(value = "General details public id", example = "e.g. fgRwCPzJnXpqCvr0XmGX", required = true)
          @PathVariable String detailsId) {
    GeneralDetailsDto detailsDto = employeeService.getEmployeeGeneralDetails(detailsId);
    GeneralDetailsRest detailsRest = mapper.map(detailsDto, GeneralDetailsRest.class);

    return EntityModel.of(detailsRest, employeeLink(empId));
  }

  // http://localhost:{port#}/{context-path}/employee/{empId}/spouse/{spouseId}
  @ApiOperation(value = "${EmployeeController.getEmployeeSpouseDetails.value}",
                notes = "${EmployeeController.getEmployeeSpouseDetails.notes}")
  @GetMapping(path = "/{empId}/spouse/{spouseId}")
  public EntityModel<SpouseRest> getEmployeeSpouseDetails(
          @ApiParam(value = "Employee public id", example = "e.g. TmpTb4efBF9IqS46Zrio", required = true)
          @PathVariable String empId,
          @ApiParam(value = "Spouse public id", example = "e.g. yFHWSuHs2DlgjikSpP02", required = true)
          @PathVariable String spouseId) {
    SpouseDto spouseDto = employeeService.getSpouse(spouseId);
    SpouseRest spouseRest = mapper.map(spouseDto, SpouseRest.class);

    return EntityModel.of(spouseRest, employeeLink(empId));
  }

  // http://localhost:{port#}/{context-path}/employee/{empId}/address/{addressId}
  @ApiOperation(value = "${EmployeeController.getEmployeeAddress.value}",
                notes = "${EmployeeController.getEmployeeAddress.notes}")
  @GetMapping(path = "/{empId}/address/{addressId}")
  public EntityModel<AddressRest> getEmployeeAddress(
          @ApiParam(value = "Employee public id", example = "e.g. TmpTb4efBF9IqS46Zrio", required = true)
          @PathVariable String empId,
          @ApiParam(value = "Address public id", example = "e.g. dWgnpLpv7daEo3p8Cz3f", required = true)
          @PathVariable String addressId) {
    AddressDto addressDto = employeeService.getAddress(addressId);
    AddressRest addressRest = mapper.map(addressDto, AddressRest.class);

    return EntityModel.of(addressRest, employeeLink(empId), addressesLink(empId));
  }

  // http://localhost:{port#}/{context-path}/employee/{empId}/addresses
  @ApiOperation(value = "${EmployeeController.getEmployeeAddresses.value}",
                notes = "${EmployeeController.getEmployeeAddresses.notes}")
  @GetMapping(path = "/{empId}/addresses")
  public CollectionModel<AddressRest> getEmployeeAddresses(
          @ApiParam(value = "Employee public id", example = "e.g. TmpTb4efBF9IqS46Zrio", required = true)
          @PathVariable String empId) {
    List<AddressDto> addressDto = employeeService.getAddresses(empId);
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

  // http://localhost:{port#}/{context-path}/employee/{empId}/child/{childId}
  @ApiOperation(value = "${EmployeeController.getEmployeeChild.value}",
                notes = "${EmployeeController.getEmployeeChild.notes}")
  @GetMapping(path = "/{empId}/child/{childId}")
  public EntityModel<ChildRest> getEmployeeChild(
          @ApiParam(value = "Employee public id", example = "e.g. TmpTb4efBF9IqS46Zrio", required = true)
          @PathVariable String empId,
          @ApiParam(value = "Child public id", example = "e.g. Jv0aPkvQcG8D53hiRzM2", required = true)
          @PathVariable String childId) {
    ChildDto childDto = employeeService.getChild(childId);
    ChildRest childRest = mapper.map(childDto, ChildRest.class);

    return EntityModel.of(childRest, employeeLink(empId), childrenLink(empId));
  }

  // http://localhost:{port#}/{context-path}/employee/{empId}/children
  @ApiOperation(value = "${EmployeeController.getChildren.value}",
                notes = "${EmployeeController.getChildren.notes}")
  @GetMapping(path = "/{empId}/children")
  public CollectionModel<ChildRest> getChildren(
          @ApiParam(value = "Employee public id", example = "e.g. TmpTb4efBF9IqS46Zrio", required = true)
          @PathVariable String empId) {
    List<ChildDto> childDtoList = employeeService.getChildren(empId);
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
