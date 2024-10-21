package com.spring.docker.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.docker.poc.kafka.KafkaSenderService;
import com.spring.docker.poc.model.LibraryRecord;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class KafkaSenderController {

	@Autowired
	private KafkaSenderService kafkaSenderService;
	
	@PostMapping("/kafka/library")
	public ResponseEntity<LibraryRecord> createTutorialKafka(@RequestBody LibraryRecord libRecord) throws JsonProcessingException {
		
		log.info("Storing Book");
		if(libRecord!=null) {
			kafkaSenderService.sendObjectToBucket(libRecord);
			return new ResponseEntity<>(libRecord, HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
