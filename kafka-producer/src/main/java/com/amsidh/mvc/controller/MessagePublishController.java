package com.amsidh.mvc.controller;

import com.amsidh.mvc.model.PersonRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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
		Optional.ofNullable(personRequest).ifPresent(person -> {
			if (null == person.getUuid()) {
				person.setUuid(UUID.randomUUID().toString());
			}
		});
		ListenableFuture<SendResult<String, Object>> sendResultListenableFuture = kafkaTemplate.send(KAFKA_TOPIC_NAME, personRequest.getUuid(), personRequest);
		sendResultListenableFuture.addCallback(new ListenableFutureCallback() {

			@Override
			public void onSuccess(Object result) {
				log.info("Message published successfully {}", result.toString());
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Failed to publish message", ex);
			}
		});
		return "Message published successfully";
	}

	/*
	 * @KafkaListener(topics = KAFKA_TOPIC_NAME, groupId = "test-consumer-group")
	 * public void consumeMessage(String message) { log.info("Message consumed {}",
	 * message); }
	 */
}
