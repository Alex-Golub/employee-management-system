package edu.mrdrprof.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeManagementSystemApplication {
  public static void main(String[] args) {
    SpringApplication.run(EmployeeManagementSystemApplication.class, args);
    System.out.println("🚀");
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
