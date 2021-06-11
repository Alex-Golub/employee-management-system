package edu.mrdrprof.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:swagger.properties")
@EnableAspectJAutoProxy
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
