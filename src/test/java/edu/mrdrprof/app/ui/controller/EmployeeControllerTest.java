package edu.mrdrprof.app.ui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.mrdrprof.app.DataGenerator;
import edu.mrdrprof.app.service.EmployeeService;
import edu.mrdrprof.app.shared.EmployeeDto;
import edu.mrdrprof.app.ui.model.request.EmployeeRequestModel;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author Alex Golub
 * @since 11-Jun-21, 6:22 PM
 */
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest extends DataGenerator {
  public static final String BASE_URL = "/api/employee";
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private EmployeeService employeeService;
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private ObjectMapper objectMapper;

  private EmployeeDto validEmployeeDto;

  @BeforeEach
  void setUp() {
    validEmployeeDto = EmployeeDto.builder()
            .id(1L)
            .publicId(EMPLOYEE_PUBLIC_ID)
            .generalDetails(getGeneralDetailsDto())
            .spouse(getSpouseDto())
            .addresses(Lists.list(getAddressDto1(), getAddressDto2()))
            .children(Lists.list(getChildDto1(), getChildDto2()))
            .build();
  }

  @Test
  @Disabled
  void createEmployee() throws Exception {
    EmployeeRequestModel model = EmployeeRequestModel.builder()
            .generalDetails(getDetailsRequestModel())
            .spouse(getSpouseRequestModel())
            .addresses(Lists.list(getAddressRequestModel1(), getAddressRequestModel2()))
            .children(Lists.list(getChildRequestModel1(), getChildRequestModel2()))
            .build();

    String body = objectMapper.writeValueAsString(model);
    employeeService.createEmployee(validEmployeeDto);
    verify(employeeService, times(1)).createEmployee(validEmployeeDto);

    MvcResult mvcResult = mockMvc.perform(post(BASE_URL).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(body))
            .andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(HttpStatus.CREATED.value(), status);
  }
}
