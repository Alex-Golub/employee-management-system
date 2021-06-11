package edu.mrdrprof.app;

import org.springframework.beans.factory.annotation.Value;
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
  @Value("${contact.name}") private String name;
  @Value("${contact.url}") private String url;
  @Value("${contact.email}") private String email;
  @Value("${apiInfo.title}") private String title;
  @Value("${apiInfo.description}") private String description;
  @Value("${apiInfo.version}") private String version;

  @Bean
  public Docket apiDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
            .protocols(new LinkedHashSet<>(Arrays.asList("HTTP", "HTTPS")))
            .apiInfo(getApiInfo())
            .select()
//            .apis(RequestHandlerSelectors.basePackage("edu.mrdrprof.app")) // actuator endpoints are hidden
            .apis(RequestHandlerSelectors.any()) // exposes all application endpoints
            .paths(PathSelectors.any()) // scan all available controller methods
            .build();
  }

  private ApiInfo getApiInfo() {
    return new ApiInfo(
            title,
            description,
            version,
            "#",
            new Contact(name, url, email),
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            Collections.emptyList()
    );
  }
}
