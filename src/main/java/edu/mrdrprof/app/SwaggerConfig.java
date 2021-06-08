package edu.mrdrprof.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * @author Alex Golub
 * @since 08-Jun-21, 3:44 PM
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  Contact contact = new Contact(
          "Alex Golub",
          "https://github.com/Alex-Golub",
          "email@email.com"
  );

  ApiInfo apiInfo = new ApiInfo(
          "Employee-Management-System",
          "This page documents Employee-Management-System end points",
          "1.0",
          "#",
          contact,
          "Apache 2.0",
          "http://www.apache.org/licenses/LICENSE-2.0",
          Collections.emptyList()
  );

  @Bean
  public Docket apiDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
            .protocols(new LinkedHashSet<>(Arrays.asList("HTTP", "HTTPS")))
            .apiInfo(apiInfo)
            .select()
            .apis(RequestHandlerSelectors.basePackage("edu.mrdrprof.app"))
            .paths(PathSelectors.any()) // scan all available controller methods
            .build();
  }
}
