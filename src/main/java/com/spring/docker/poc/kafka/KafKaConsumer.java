package com.spring.docker.poc.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafKaConsumer {

	
@KafkaListener(topics = "library-kafka-bucket",groupId = "we-app-group")
public void consume(String message){
log.info(String.format("Message received -> %s", message));
}


}
