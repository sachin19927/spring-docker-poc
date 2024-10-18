package com.spring.docker.poc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SampleController {

	@GetMapping("/version")
	public String testMethod()
	{
		log.info("calling Version Method");
		return "v6";
	}
}
