package com.spring.docker.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.docker.poc.email.EmailService;
import com.spring.docker.poc.model.LibraryRecord;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Library", description = "Library Management APIs")
public class LibraryContoller {
	
	@Autowired
	private EmailService emailService;
	
	

	@PostMapping("/library")
	public ResponseEntity<LibraryRecord> createTutorial(@RequestBody LibraryRecord libRecord) {
		
		log.info("Storing Book");
		if(libRecord!=null) {
			emailService.sendOnBoardMail(libRecord);
			return new ResponseEntity<>(libRecord, HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
