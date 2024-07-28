package com.spring.docker.poc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	@GetMapping("/version")
	public String testMethod()
	{
		return "v3";
	}
}
