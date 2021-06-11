package edu.mrdrprof.app;

import edu.mrdrprof.app.io.entity.*;
import edu.mrdrprof.app.shared.*;
import edu.mrdrprof.app.ui.model.request.AddressRequestModel;
import edu.mrdrprof.app.ui.model.request.ChildRequestModel;
import edu.mrdrprof.app.ui.model.request.GeneralDetailsRequestModel;
import edu.mrdrprof.app.ui.model.request.SpouseRequestModel;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

/**
 * @author Alex Golub
 * @since 11-Jun-21, 6:58 PM
 */
public class DataGenerator {
  protected final String EMPLOYEE_PUBLIC_ID = "P75ffeWvS1i887jElo0b";
  protected final String DETAILS_PUBLIC_ID = "p3Z1TB2osl9hOhfDF2lJ";
  protected final String ADDRESS_1_PUBLIC_ID = "xM4zLIIiPVhZBp3asy7W";
  protected final String ADDRESS_2_PUBLIC_ID = "rbbG1o5FzsHinCyH8V1r";
  protected final String CHILD_1_PUBLIC_ID = "OVc2OPUzbnrBDoZvmit0";
  protected final String CHILD_2_PUBLIC_ID = "Y1wR8o7mVAlPRJCngnUB";

  protected EmployeeDto getEmployeeDto() {
    return EmployeeDto.builder()
            .generalDetails(getGeneralDetailsDto())
            .spouse(getSpouseDto())
            .addresses(Lists.list(getAddressDto1(), getAddressDto2()))
            .children(Lists.list(getChildDto1(), getChildDto2()))
            .build();
  }

  protected AddressDto getAddressDto1() {
    AddressDto addressDto = new AddressDto();
    BeanUtils.copyProperties(getAddress1(), addressDto);
    return addressDto;
  }

  protected AddressDto getAddressDto2() {
    AddressDto addressDto = new AddressDto();
    BeanUtils.copyProperties(getAddress2(), addressDto);
    return addressDto;
  }

  protected Address getAddress1() {
    return Address.builder()
            .publicId(ADDRESS_1_PUBLIC_ID)
            .city("Tel-Aviv")
            .country("Israel")
            .streetName("Dizengoff")
            .postalCode("42")
            .type(AddressType.SHIPPING)
            .build();
  }

  protected Address getAddress2() {
    return Address.builder()
            .publicId(ADDRESS_2_PUBLIC_ID)
            .city("Haifa")
            .country("Israel")
            .streetName("Allenby")
            .postalCode("24")
            .type(AddressType.BILLING)
            .build();
  }

  protected ChildDto getChildDto1() {
    ChildDto childDto = new ChildDto();
    BeanUtils.copyProperties(getChild1(), childDto);
    return childDto;
  }

  protected ChildDto getChildDto2() {
    ChildDto childDto = new ChildDto();
    BeanUtils.copyProperties(getChild2(), childDto);
    return childDto;
  }

  protected Child getChild1() {
    return Child.builder()
            .id(1L)
            .publicId(CHILD_1_PUBLIC_ID)
            .firstName("Mark")
            .lastName("Doe")
            .birthDate(new Date(LocalDate.of(2000, Month.JANUARY, 1).toEpochDay()))
            .build();
  }

  protected Child getChild2() {
    return Child.builder()
            .id(2L)
            .publicId(CHILD_2_PUBLIC_ID)
            .firstName("Sophie")
            .lastName("Doe")
            .birthDate(new Date(LocalDate.of(2010, Month.FEBRUARY, 1).toEpochDay()))
            .build();
  }

  protected SpouseDto getSpouseDto() {
    SpouseDto spouseDto = new SpouseDto();
    BeanUtils.copyProperties(getSpouse(), spouseDto);
    return spouseDto;
  }

  protected Spouse getSpouse() {
    return Spouse.builder()
            .id(1L)
            .firstName("Jane")
            .lastName("Doe")
            .email("jane@email.com")
            .phoneNumber("987-654-321")
            .ssn("4321")
            .sex(Sex.FEMALE)
            .build();
  }

  protected GeneralDetailsDto getGeneralDetailsDto() {
    GeneralDetailsDto generalDetails = new GeneralDetailsDto();
    BeanUtils.copyProperties(getGeneralDetails(), generalDetails);
    return generalDetails;
  }

  protected GeneralDetails getGeneralDetails() {
    return GeneralDetails.builder()
            .id(1L)
            .publicId(DETAILS_PUBLIC_ID)
            .firstName("Bob")
            .lastName("Doe")
            .email("bob@email.com")
            .phoneNumber("123-456-789")
            .ssn("1234")
            .sex(Sex.MALE)
            .hireDate(new Date(LocalDate.of(2025, Month.JANUARY, 1).toEpochDay()))
            .build();
  }

  protected GeneralDetailsRequestModel getDetailsRequestModel() {
    GeneralDetailsRequestModel detailsRequestModel = new GeneralDetailsRequestModel();
    BeanUtils.copyProperties(getGeneralDetails(), detailsRequestModel, "id", "publicId");
    return detailsRequestModel;
  }

  protected SpouseRequestModel getSpouseRequestModel() {
    SpouseRequestModel spouseRequestModel = new SpouseRequestModel();
    BeanUtils.copyProperties(getSpouse(), spouseRequestModel, "id", "publicId");
    return spouseRequestModel;
  }

  protected AddressRequestModel getAddressRequestModel1() {
    AddressRequestModel addressRequestModel = new AddressRequestModel();
    BeanUtils.copyProperties(getAddress1(), addressRequestModel, "id", "publicId");
    return addressRequestModel;
  }

  protected AddressRequestModel getAddressRequestModel2() {
    AddressRequestModel addressRequestModel = new AddressRequestModel();
    BeanUtils.copyProperties(getAddress2(), addressRequestModel, "id", "publicId");
    return addressRequestModel;
  }

  protected ChildRequestModel getChildRequestModel1() {
    ChildRequestModel childRequestModel = new ChildRequestModel();
    BeanUtils.copyProperties(getChild1(), childRequestModel, "id", "publicId");
    return childRequestModel;
  }

  protected ChildRequestModel getChildRequestModel2() {
    ChildRequestModel childRequestModel = new ChildRequestModel();
    BeanUtils.copyProperties(getChild2(), childRequestModel, "id", "publicId");
    return childRequestModel;
  }
}
