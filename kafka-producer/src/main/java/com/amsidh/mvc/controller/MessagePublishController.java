package com.amsidh.mvc.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amsidh.mvc.model.PersonRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MessagePublishController {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final static String KAFKA_TOPIC_NAME = "spring-topic";

	@GetMapping("/publish/{name}")
	public String publishMessage(@PathVariable String name) {
		log.info("Sending message {}", name);
		kafkaTemplate.send(KAFKA_TOPIC_NAME, "Hello " + name);
		return "Message published successfully";
	}
	
	@PostMapping("/publish")
	public String publishMessage( @RequestBody PersonRequest personRequest) {
		log.info("Sending person {}", personRequest);
        kafkaTemplate.send(KAFKA_TOPIC_NAME, personRequest);
		return "Message published successfully";
	}

	/*
	 * @KafkaListener(topics = KAFKA_TOPIC_NAME, groupId = "test-consumer-group")
	 * public void consumeMessage(String message) { log.info("Message consumed {}",
	 * message); }
	 */
}
