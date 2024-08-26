package com.spring.docker.poc.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "swagger.openapi")
public class SwaggerProps {

	private String localUrl;
	private String localDesc;
	private String devUrl;
	private String devDesc;
	private String testUrl;
	private String testDesc;
	private String stageUrl;
	private String stageDesc;
	private String prodUrl;
	private String prodDesc;
	
	private String contactEmail;
	private String contactName;
	private String contactUrl;
	private String licenseName;
	private String licenseUrl;
	private String infoTitle;
	private String infoVersion;
	private String infoDesc;
	private String infoTermService;
	
	
}
