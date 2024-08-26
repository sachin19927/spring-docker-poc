package com.spring.docker.poc.swagger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerOpenAPIConfig {

	@Autowired
	private SwaggerProps swaggerProps;
	
	private static final String SERVER_DESC = "Server URL in ";
	
	@Bean
	public OpenAPI myOpenAPI() {
		Server localServer = new Server();
		localServer.setUrl(swaggerProps.getLocalUrl());
		localServer.setDescription(SERVER_DESC+swaggerProps.getLocalDesc());
		
		Server devServer = new Server();
		devServer.setUrl(swaggerProps.getDevUrl());
		devServer.setDescription(SERVER_DESC+swaggerProps.getDevDesc());
		
		Server testServer = new Server();
		testServer.setUrl(swaggerProps.getTestUrl());
		testServer.setDescription(SERVER_DESC+swaggerProps.getTestDesc());
		
		Server stageServer = new Server();
		stageServer.setUrl(swaggerProps.getStageUrl());
		stageServer.setDescription(SERVER_DESC+swaggerProps.getStageDesc());

		Server prodServer = new Server();
		prodServer.setUrl(swaggerProps.getProdUrl());
		prodServer.setDescription(SERVER_DESC+swaggerProps.getProdDesc());

		
		Contact contact = new Contact();
		contact.setEmail(swaggerProps.getContactEmail());
		contact.setName(swaggerProps.getContactName());
		contact.setUrl(swaggerProps.getContactUrl());

		License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

		Info info = new Info()
				.title(swaggerProps.getInfoTitle())
				.version(swaggerProps.getInfoVersion())
				.contact(contact)
				.description(swaggerProps.getInfoDesc())
				.termsOfService(swaggerProps.getInfoTermService())
				.license(mitLicense);

		return new OpenAPI().info(info).servers(List.of(localServer,devServer,
				testServer,stageServer,prodServer));
	}
}
