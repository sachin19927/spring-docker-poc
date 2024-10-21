package com.spring.docker.poc.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.docker.poc.model.LibraryRecord;

@Service
public class KafkaSenderService {

	@Autowired
	private KafkaProducer kafkaProducer;
	
	//@Autowired
	//private  ObjectMapper objectMapper;
	
	public void sendObjectToBucket(LibraryRecord libraryRecord) throws JsonProcessingException {
		
		//String jsonValue = objectMapper.writeValueAsString(libraryRecord);
		kafkaProducer.sendMessageToBukcet("sachin");
	}
}
