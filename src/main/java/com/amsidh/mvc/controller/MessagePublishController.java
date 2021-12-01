package com.amsidh.mvc.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MessagePublishController {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final static String KAFKA_TOPIC_NAME = "spring-topic";

	@GetMapping("/publish/{name}")
	public String publishMessage(@PathVariable String name) {
		kafkaTemplate.send(KAFKA_TOPIC_NAME, "Hi " + name + "! How are you");
		return "Message published successfully";
	}
}
