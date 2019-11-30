package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket itemApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.example.demo.controller")).build().apiInfo(metaInfo());
		
	}


    private ApiInfo metaInfo() {
        return new ApiInfoBuilder().title("Spring Boot Swagger REST API")
            .description("Employee Management REST API")
            .contact(new Contact("Israel Tuval", "www.inventory.net", "israel.tu@gmail.com"))
            .license("h2")
            .licenseUrl("http://www.h2database.com/html/license.html")
            .version("1.0")
            .build();
    }
	
}
