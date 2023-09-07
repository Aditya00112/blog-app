package com.chirantan.project.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	private ApiKey apiKeys() {
		return new ApiKey("JWT", AppConstants.AUTHORIZATION_HEADER, "header");
	}
	
	private List<SecurityContext> securityContexts(){
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
	}
	
	private List<SecurityReference> securityReferences(){
		AuthorizationScope scope= new AuthorizationScope("global", "acesseverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scope}));
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys()))
				.apiInfo(getApiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
				}

	private ApiInfo getApiInfo() {
		return new ApiInfo("Blogging Application", "This is a blogging application used for discussions", "1.0", "Terms of Sevice", 
				new Contact("Chintu", "Learning for Switch", "chintu@gmail.com"), "Licence of APIs", "API License URL- xyz@mail.com",Collections.emptyList());
	}

}
